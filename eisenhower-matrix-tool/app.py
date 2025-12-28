from flask import Flask, render_template_string, request, redirect, url_for
import sqlite3
import os
from datetime import datetime

app = Flask(__name__)

# Database setup
DB_FILE = os.path.join(os.path.dirname(os.path.abspath(__file__)), 'tasks.db')

def init_db():
    """Initialize database"""
    conn = sqlite3.connect(DB_FILE)
    cursor = conn.cursor()
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS tasks (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            title TEXT NOT NULL,
            description TEXT,
            quadrant TEXT NOT NULL,
            due_date TEXT,
            completed INTEGER DEFAULT 0,
            archived INTEGER DEFAULT 0,
            original_quadrant TEXT,
            created_at TEXT NOT NULL,
            completed_at TEXT,
            archived_at TEXT
        )
    ''')
    conn.commit()
    
    # Add test card if database is empty
    cursor.execute('SELECT COUNT(*) FROM tasks WHERE archived = 0')
    if cursor.fetchone()[0] == 0:
        cursor.execute('''
            INSERT INTO tasks (title, description, quadrant, due_date, created_at, original_quadrant)
            VALUES (?, ?, ?, ?, ?, ?)
        ''', ('Test Card', 'Click arrows to move me between quadrants!', 'do', '2025-12-27', datetime.now().isoformat(), 'do'))
        conn.commit()
    
    conn.close()

def get_tasks():
    """Get all non-archived tasks"""
    conn = sqlite3.connect(DB_FILE)
    conn.row_factory = sqlite3.Row
    cursor = conn.cursor()
    cursor.execute('SELECT * FROM tasks WHERE archived = 0 ORDER BY created_at ASC')
    tasks = [dict(row) for row in cursor.fetchall()]
    conn.close()
    
    # Organize by quadrant
    tasks_by_quadrant = {'do': [], 'decide': [], 'delegate': [], 'delete': []}
    for task in tasks:
        tasks_by_quadrant[task['quadrant']].append(task)
    
    return tasks_by_quadrant

def get_archived_tasks():
    """Get all archived tasks organized by original quadrant"""
    conn = sqlite3.connect(DB_FILE)
    conn.row_factory = sqlite3.Row
    cursor = conn.cursor()
    cursor.execute('SELECT * FROM tasks WHERE archived = 1 ORDER BY archived_at DESC')
    tasks = [dict(row) for row in cursor.fetchall()]
    conn.close()
    
    # Organize by original quadrant
    tasks_by_quadrant = {'do': [], 'decide': [], 'delegate': [], 'delete': []}
    for task in tasks:
        original = task.get('original_quadrant', task['quadrant'])
        if original in tasks_by_quadrant:
            tasks_by_quadrant[original].append(task)
    
    return tasks_by_quadrant

# Initialize database on startup
init_db()

HTML_TEMPLATE = """
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Eisenhower Matrix - Task Manager</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f5f5f5;
            padding: 20px;
        }
        
        .header {
            text-align: center;
            margin-bottom: 30px;
        }
        
        h1 {
            color: #333;
            margin-bottom: 10px;
        }
        
        .principles-btn {
            background: #4CAF50;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            text-decoration: none;
            display: inline-block;
        }
        
        .principles-btn:hover {
            background: #45a049;
        }
        
        .matrix-wrapper {
            max-width: 1300px;
            margin: 0 auto 50px;
        }
        
        .matrix-labels {
            display: grid;
            grid-template-columns: 80px 1fr;
            grid-template-rows: 50px 1fr;
            gap: 0;
        }
        
        .axis-label {
            font-size: 24px;
            font-weight: bold;
            color: #333;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        
        .axis-label-left {
            writing-mode: vertical-rl;
            transform: rotate(180deg);
            grid-column: 1;
            grid-row: 2;
        }
        
        .axis-label-top {
            grid-column: 2;
            grid-row: 1;
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
        }
        
        .axis-label-top > div {
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 24px;
            font-weight: bold;
            color: #333;
        }
        
        .matrix-container {
            grid-column: 2;
            grid-row: 2;
            display: grid;
            grid-template-columns: 1fr 1fr;
            grid-template-rows: 1fr 1fr;
            gap: 20px;
            min-height: 600px;
        }
        
        .quadrant {
            border: none;
            border-radius: 20px;
            padding: 25px;
            min-height: 250px;
            position: relative;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        
        .quadrant-number {
            position: absolute;
            top: 20px;
            right: 25px;
            font-size: 120px;
            font-weight: bold;
            opacity: 0.3;
            color: white;
            line-height: 1;
            pointer-events: none;
        }
        
        .quadrant h2 {
            margin-bottom: 5px;
            font-size: 32px;
            text-align: center;
            font-weight: bold;
            color: #000;
        }
        
        .quadrant-do {
            background: #4CAF50;
        }
        
        .quadrant-do h2 {
            color: #000;
        }
        
        .quadrant-decide {
            background: #4DD0E1;
        }
        
        .quadrant-decide h2 {
            color: #000;
        }
        
        .quadrant-delegate {
            background: #EF5350;
        }
        
        .quadrant-delegate h2 {
            color: #000;
        }
        
        .quadrant-delete {
            background: #BDBDBD;
        }
        
        .quadrant-delete h2 {
            color: #000;
        }
        
        .task-card {
            background: white;
            border: 1px solid #ccc;
            border-radius: 8px;
            padding: 15px;
            margin-bottom: 10px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        
        .task-card.overdue {
            background: #ffebee;
            border: 2px solid #d32f2f;
            box-shadow: 0 4px 8px rgba(211,47,47,0.3);
        }
        
        .task-card.overdue .task-title {
            color: #b71c1c;
        }
        
        .overdue-badge {
            display: inline-block;
            background: #d32f2f;
            color: white;
            padding: 2px 8px;
            border-radius: 3px;
            font-size: 11px;
            font-weight: bold;
            margin-left: 8px;
        }
        
        .task-title {
            font-weight: bold;
            font-size: 16px;
            color: #333;
            margin-bottom: 5px;
        }
        
        .task-due-date {
            font-size: 13px;
            color: #666;
            margin-bottom: 8px;
        }
        
        .task-due-date.overdue {
            color: #d32f2f;
            font-weight: bold;
        }
        
        .task-description {
            font-size: 14px;
            color: #666;
            margin-bottom: 10px;
        }
        
        .quadrant-subtitle {
            text-align: center;
            font-size: 16px;
            color: #000;
            font-style: italic;
            margin-bottom: 20px;
            font-weight: 400;
        }
        
        .move-buttons {
            display: flex;
            gap: 5px;
            flex-wrap: wrap;
        }
        
        .move-btn {
            background: #2196F3;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 12px;
        }
        
        .move-btn:hover {
            background: #1976D2;
        }
        
        .archive-btn {
            background: #9e9e9e;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 12px;
        }
        
        .archive-btn:hover {
            background: #757575;
        }
        
        .edit-btn {
            background: #ff9800;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 12px;
        }
        
        .edit-btn:hover {
            background: #f57c00;
        }
        
        .empty-message {
            text-align: center;
            color: #999;
            font-style: italic;
            padding: 20px;
        }
        
        .archived-section {
            margin-top: 40px;
            padding-top: 40px;
            border-top: 3px solid #ddd;
        }
        
        .archived-header {
            text-align: center;
            margin-bottom: 20px;
        }
        
        .archived-header h2 {
            color: #666;
            font-size: 24px;
        }
        
        .archived-task-card {
            background: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 15px;
            margin-bottom: 10px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.08);
            opacity: 0.8;
        }
        
        .archived-badge {
            display: inline-block;
            background: #757575;
            color: white;
            padding: 2px 8px;
            border-radius: 3px;
            font-size: 11px;
            margin-left: 8px;
        }
        
        .add-task-section {
            max-width: 600px;
            margin: 0 auto 30px;
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }
        
        .add-task-section h2 {
            margin-top: 0;
            color: #333;
            font-size: 20px;
        }
        
        .form-group {
            margin-bottom: 15px;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #555;
        }
        
        .form-group input,
        .form-group textarea,
        .form-group select {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
            box-sizing: border-box;
        }
        
        .form-group textarea {
            resize: vertical;
            min-height: 60px;
        }
        
        .form-group select {
            cursor: pointer;
        }
        
        .submit-btn {
            background: #4CAF50;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            width: 100%;
        }
        
        .submit-btn:hover {
            background: #45a049;
        }
        
        .toggle-form-btn {
            background: #2196F3;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            margin-left: 10px;
        }
        
        .toggle-form-btn:hover {
            background: #1976D2;
        }
        
        .hidden {
            display: none;
        }
        
        .required {
            color: red;
        }
        
        .char-count {
            font-size: 12px;
            color: #666;
            text-align: right;
        }
        
        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0,0,0,0.4);
        }
        
        .modal.show {
            display: block;
        }
        
        .modal-content {
            background-color: white;
            margin: 5% auto;
            padding: 30px;
            border: 1px solid #888;
            border-radius: 10px;
            width: 90%;
            max-width: 600px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.3);
        }
        
        .close-modal {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
            line-height: 20px;
        }
        
        .close-modal:hover,
        .close-modal:focus {
            color: #000;
        }
        
        .modal-header {
            margin-bottom: 20px;
        }
        
        .modal-header h2 {
            margin: 0;
            color: #333;
        }
    </style>
    <script>
        // Preserve scroll position on page reload
        window.addEventListener('beforeunload', function() {
            sessionStorage.setItem('scrollPos', window.scrollY);
        });
        
        window.addEventListener('load', function() {
            var scrollPos = sessionStorage.getItem('scrollPos');
            if (scrollPos) {
                window.scrollTo(0, parseInt(scrollPos));
                sessionStorage.removeItem('scrollPos');
            }
        });
        
        // Edit modal functions
        function showEditModal(taskId, title, description, quadrant, dueDate) {
            document.getElementById('editModal').classList.add('show');
            document.getElementById('editTaskId').value = taskId;
            document.getElementById('editTitle').value = title;
            document.getElementById('editDescription').value = description || '';
            document.getElementById('editQuadrant').value = quadrant;
            document.getElementById('editDueDate').value = dueDate || '';
        }
        
        function closeEditModal() {
            document.getElementById('editModal').classList.remove('show');
        }
        
        // Close modal if clicking outside of it
        window.onclick = function(event) {
            var modal = document.getElementById('editModal');
            if (event.target == modal) {
                closeEditModal();
            }
        }
    </script>
</head>
<body>
    <div class="header">
        <h1>Eisenhower Matrix Task Manager</h1>
        <a href="/principles" class="principles-btn">View Principles</a>
        <a href="/" class="toggle-form-btn" onclick="event.preventDefault(); document.getElementById('addTaskSection').classList.toggle('hidden');">➕ Add Task</a>
    </div>
    
    <!-- Edit Task Modal -->
    <div id="editModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <span class="close-modal" onclick="closeEditModal()">&times;</span>
                <h2>Edit Task</h2>
            </div>
            <form method="POST" action="/edit" id="editForm">
                <input type="hidden" id="editTaskId" name="task_id">
                
                <div class="form-group">
                    <label for="editTitle">Title <span class="required">*</span></label>
                    <input type="text" id="editTitle" name="title" required maxlength="100" placeholder="Enter task title">
                    <div class="char-count">Max 100 characters</div>
                </div>
                
                <div class="form-group">
                    <label for="editDescription">Description</label>
                    <textarea id="editDescription" name="description" maxlength="500" placeholder="Enter task description (optional)"></textarea>
                    <div class="char-count">Max 500 characters</div>
                </div>
                
                <div class="form-group">
                    <label for="editQuadrant">Quadrant <span class="required">*</span></label>
                    <select id="editQuadrant" name="quadrant" required>
                        <option value="do">DO - Urgent & Important (Do it now)</option>
                        <option value="decide">DECIDE - Not Urgent & Important (Schedule it)</option>
                        <option value="delegate">DELEGATE - Urgent & Not Important (Assign it)</option>
                        <option value="delete">DELETE - Neither (Eliminate it)</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="editDueDate">Due Date</label>
                    <input type="date" id="editDueDate" name="due_date">
                </div>
                
                <button type="submit" class="submit-btn">Save Changes</button>
            </form>
        </div>
    </div>
    
    <div id="addTaskSection" class="add-task-section">
        <h2>Add New Task</h2>
        <form method="POST" action="/add">
            <div class="form-group">
                <label for="title">Title <span class="required">*</span></label>
                <input type="text" id="title" name="title" required maxlength="100" placeholder="Enter task title">
                <div class="char-count">Max 100 characters</div>
            </div>
            
            <div class="form-group">
                <label for="description">Description</label>
                <textarea id="description" name="description" maxlength="500" placeholder="Enter task description (optional)"></textarea>
                <div class="char-count">Max 500 characters</div>
            </div>
            
            <div class="form-group">
                <label for="quadrant">Quadrant <span class="required">*</span></label>
                <select id="quadrant" name="quadrant" required>
                    <option value="">-- Select Quadrant --</option>
                    <option value="do">DO - Urgent & Important (Do it now)</option>
                    <option value="decide">DECIDE - Not Urgent & Important (Schedule it)</option>
                    <option value="delegate">DELEGATE - Urgent & Not Important (Assign it)</option>
                    <option value="delete">DELETE - Neither (Eliminate it)</option>
                </select>
            </div>
            
            <div class="form-group">
                <label for="due_date">Due Date</label>
                <input type="date" id="due_date" name="due_date">
            </div>
            
            <button type="submit" class="submit-btn">Add Task</button>
        </form>
    </div>
    
    <div class="matrix-wrapper">
        <div class="matrix-labels">
            <div class="axis-label axis-label-left">Important</div>
            <div class="axis-label axis-label-top">
                <div>Urgent</div>
                <div>Not Urgent</div>
            </div>
            <div class="matrix-container">
                <div class="quadrant quadrant-do">
                    <div class="quadrant-number">1</div>
                    <h2>Do</h2>
                    <div class="quadrant-subtitle">Do it now.</div>
            {% if tasks['do'] %}
                {% for task in tasks['do'] %}
                <div class="task-card{% if is_overdue(task) %} overdue{% endif %}">
                    <div class="task-title">
                        {{ task.title }}
                        {% if is_overdue(task) %}<span class="overdue-badge">OVERDUE</span>{% endif %}
                    </div>
                    <div class="task-description">{{ task.description }}</div>
                    {% if task.due_date %}
                    <div class="task-due-date{% if is_overdue(task) %} overdue{% endif %}">Due: {{ task.due_date }}</div>
                    {% endif %}
                    <div class="move-buttons">
                        <button type="button" class="edit-btn" onclick="showEditModal({{ task.id }}, '{{ task.title|replace("'", "\\'")|replace('"', '&quot;') }}', '{{ task.description|replace("'", "\\'")|replace('"', '&quot;') }}', '{{ task.quadrant }}', '{{ task.due_date or '' }}')">✏️ Edit</button>
                        <form method="POST" action="/move/{{ task.id }}/decide" style="display: inline;">
                            <button type="submit" class="move-btn">→ Decide</button>
                        </form>
                        <form method="POST" action="/move/{{ task.id }}/delegate" style="display: inline;">
                            <button type="submit" class="move-btn">↓ Delegate</button>
                        </form>
                        <form method="POST" action="/move/{{ task.id }}/delete" style="display: inline;">
                            <button type="submit" class="move-btn">↘ Delete</button>
                        </form>
                        <form method="POST" action="/archive/{{ task.id }}" style="display: inline;">
                            <button type="submit" class="archive-btn">📦 Archive</button>
                        </form>
                    </div>
                </div>
                {% endfor %}
            {% else %}
                <div class="empty-message">No tasks here</div>
            {% endif %}
        </div>
        
                <div class="quadrant quadrant-decide">
                    <div class="quadrant-number">2</div>
                    <h2>Decide</h2>
                    <div class="quadrant-subtitle">Schedule a time to do it</div>
            {% if tasks['decide'] %}
                {% for task in tasks['decide'] %}
                <div class="task-card{% if is_overdue(task) %} overdue{% endif %}">
                    <div class="task-title">
                        {{ task.title }}
                        {% if is_overdue(task) %}<span class="overdue-badge">OVERDUE</span>{% endif %}
                    </div>
                    <div class="task-description">{{ task.description }}</div>
                    {% if task.due_date %}
                    <div class="task-due-date{% if is_overdue(task) %} overdue{% endif %}">Due: {{ task.due_date }}</div>
                    {% endif %}
                    <div class="move-buttons">
                        <button type="button" class="edit-btn" onclick="showEditModal({{ task.id }}, '{{ task.title|replace("'", "\\'")|replace('"', '&quot;') }}', '{{ task.description|replace("'", "\\'")|replace('"', '&quot;') }}', '{{ task.quadrant }}', '{{ task.due_date or '' }}')">✏️ Edit</button>
                        <form method="POST" action="/move/{{ task.id }}/do" style="display: inline;">
                            <button type="submit" class="move-btn">← Do</button>
                        </form>
                        <form method="POST" action="/move/{{ task.id }}/delegate" style="display: inline;">
                            <button type="submit" class="move-btn">↙ Delegate</button>
                        </form>
                        <form method="POST" action="/move/{{ task.id }}/delete" style="display: inline;">
                            <button type="submit" class="move-btn">↓ Delete</button>
                        </form>
                        <form method="POST" action="/archive/{{ task.id }}" style="display: inline;">
                            <button type="submit" class="archive-btn">📦 Archive</button>
                        </form>
                    </div>
                </div>
                {% endfor %}
            {% else %}
                <div class="empty-message">No tasks here</div>
            {% endif %}
        </div>
        
                <div class="quadrant quadrant-delegate">
                    <div class="quadrant-number">3</div>
                    <h2>Delegate</h2>
                    <div class="quadrant-subtitle">Who can do it for you?</div>
            {% if tasks['delegate'] %}
                {% for task in tasks['delegate'] %}
                <div class="task-card{% if is_overdue(task) %} overdue{% endif %}">
                    <div class="task-title">
                        {{ task.title }}
                        {% if is_overdue(task) %}<span class="overdue-badge">OVERDUE</span>{% endif %}
                    </div>
                    <div class="task-description">{{ task.description }}</div>
                    {% if task.due_date %}
                    <div class="task-due-date{% if is_overdue(task) %} overdue{% endif %}">Due: {{ task.due_date }}</div>
                    {% endif %}
                    <div class="move-buttons">
                        <button type="button" class="edit-btn" onclick="showEditModal({{ task.id }}, '{{ task.title|replace("'", "\\'")|replace('"', '&quot;') }}', '{{ task.description|replace("'", "\\'")|replace('"', '&quot;') }}', '{{ task.quadrant }}', '{{ task.due_date or '' }}')">✏️ Edit</button>
                        <form method="POST" action="/move/{{ task.id }}/do" style="display: inline;">
                            <button type="submit" class="move-btn">↑ Do</button>
                        </form>
                        <form method="POST" action="/move/{{ task.id }}/decide" style="display: inline;">
                            <button type="submit" class="move-btn">↗ Decide</button>
                        </form>
                        <form method="POST" action="/move/{{ task.id }}/delete" style="display: inline;">
                            <button type="submit" class="move-btn">→ Delete</button>
                        </form>
                        <form method="POST" action="/archive/{{ task.id }}" style="display: inline;">
                            <button type="submit" class="archive-btn">📦 Archive</button>
                        </form>
                    </div>
                </div>
                {% endfor %}
            {% else %}
                <div class="empty-message">No tasks here</div>
            {% endif %}
        </div>
        
                <div class="quadrant quadrant-delete">
                    <div class="quadrant-number">4</div>
                    <h2>Delete</h2>
                    <div class="quadrant-subtitle">Eliminate it</div>
            {% if tasks['delete'] %}
                {% for task in tasks['delete'] %}
                <div class="task-card{% if is_overdue(task) %} overdue{% endif %}">
                    <div class="task-title">
                        {{ task.title }}
                        {% if is_overdue(task) %}<span class="overdue-badge">OVERDUE</span>{% endif %}
                    </div>
                    <div class="task-description">{{ task.description }}</div>
                    {% if task.due_date %}
                    <div class="task-due-date{% if is_overdue(task) %} overdue{% endif %}">Due: {{ task.due_date }}</div>
                    {% endif %}
                    <div class="move-buttons">
                        <button type="button" class="edit-btn" onclick="showEditModal({{ task.id }}, '{{ task.title|replace("'", "\\'")|replace('"', '&quot;') }}', '{{ task.description|replace("'", "\\'")|replace('"', '&quot;') }}', '{{ task.quadrant }}', '{{ task.due_date or '' }}')">✏️ Edit</button>
                        <form method="POST" action="/move/{{ task.id }}/do" style="display: inline;">
                            <button type="submit" class="move-btn">↖ Do</button>
                        </form>
                        <form method="POST" action="/move/{{ task.id }}/decide" style="display: inline;">
                            <button type="submit" class="move-btn">↑ Decide</button>
                        </form>
                        <form method="POST" action="/move/{{ task.id }}/delegate" style="display: inline;">
                            <button type="submit" class="move-btn">← Delegate</button>
                        </form>
                        <form method="POST" action="/archive/{{ task.id }}" style="display: inline;">
                            <button type="submit" class="archive-btn">📦 Archive</button>
                        </form>
                    </div>
                </div>
                {% endfor %}
            {% else %}
                <div class="empty-message">No tasks here</div>
            {% endif %}
        </div>
            </div>
        </div>
    </div>
    
    <!-- Archived Eisenhower Matrix -->
    <div class="archived-section">
        <div class="archived-header">
            <h2>📦 Archived Eisenhower Matrix</h2>
            <p style="color: #666;">Tasks are shown in their original quadrants</p>
        </div>
        
        <div class="matrix-wrapper">
            <div class="matrix-labels">
                <div class="axis-label axis-label-left">Important</div>
                <div class="axis-label axis-label-top">
                    <div>Urgent</div>
                    <div>Not Urgent</div>
                </div>
                <div class="matrix-container">
                    <div class="quadrant quadrant-do">
                        <div class="quadrant-number">1</div>
                        <h2>Do <span class="archived-badge">Archived</span></h2>
                        <div class="quadrant-subtitle">Do it now.</div>
                        {% if archived_tasks['do'] %}
                            {% for task in archived_tasks['do'] %}
                            <div class="archived-task-card">
                                <div class="task-title">{{ task.title }}</div>
                                <div class="task-description">{{ task.description }}</div>
                                {% if task.due_date %}
                                <div class="task-description">Due: {{ task.due_date }}</div>
                                {% endif %}
                                {% if task.archived_at %}
                                <div class="task-description" style="font-size: 12px; color: #999;">Archived: {{ task.archived_at[:10] }}</div>
                                {% endif %}
                            </div>
                            {% endfor %}
                        {% else %}
                            <div class="empty-message">No archived tasks</div>
                        {% endif %}
                    </div>
            
                    <div class="quadrant quadrant-decide">
                        <div class="quadrant-number">2</div>
                        <h2>Decide <span class="archived-badge">Archived</span></h2>
                        <div class="quadrant-subtitle">Schedule a time to do it</div>
                        {% if archived_tasks['decide'] %}
                            {% for task in archived_tasks['decide'] %}
                            <div class="archived-task-card">
                                <div class="task-title">{{ task.title }}</div>
                                <div class="task-description">{{ task.description }}</div>
                                {% if task.due_date %}
                                <div class="task-description">Due: {{ task.due_date }}</div>
                                {% endif %}
                                {% if task.archived_at %}
                                <div class="task-description" style="font-size: 12px; color: #999;">Archived: {{ task.archived_at[:10] }}</div>
                                {% endif %}
                            </div>
                            {% endfor %}
                        {% else %}
                            <div class="empty-message">No archived tasks</div>
                        {% endif %}
                    </div>
            
                    <div class="quadrant quadrant-delegate">
                        <div class="quadrant-number">3</div>
                        <h2>Delegate <span class="archived-badge">Archived</span></h2>
                        <div class="quadrant-subtitle">Who can do it for you?</div>
                        {% if archived_tasks['delegate'] %}
                            {% for task in archived_tasks['delegate'] %}
                            <div class="archived-task-card">
                                <div class="task-title">{{ task.title }}</div>
                                <div class="task-description">{{ task.description }}</div>
                                {% if task.due_date %}
                                <div class="task-description">Due: {{ task.due_date }}</div>
                                {% endif %}
                                {% if task.archived_at %}
                                <div class="task-description" style="font-size: 12px; color: #999;">Archived: {{ task.archived_at[:10] }}</div>
                                {% endif %}
                            </div>
                            {% endfor %}
                        {% else %}
                            <div class="empty-message">No archived tasks</div>
                        {% endif %}
                    </div>
            
                    <div class="quadrant quadrant-delete">
                        <div class="quadrant-number">4</div>
                        <h2>Delete <span class="archived-badge">Archived</span></h2>
                        <div class="quadrant-subtitle">Eliminate it</div>
                        {% if archived_tasks['delete'] %}
                            {% for task in archived_tasks['delete'] %}
                            <div class="archived-task-card">
                                <div class="task-title">{{ task.title }}</div>
                                <div class="task-description">{{ task.description }}</div>
                                {% if task.due_date %}
                                <div class="task-description">Due: {{ task.due_date }}</div>
                                {% endif %}
                                {% if task.archived_at %}
                                <div class="task-description" style="font-size: 12px; color: #999;">Archived: {{ task.archived_at[:10] }}</div>
                                {% endif %}
                            </div>
                            {% endfor %}
                        {% else %}
                            <div class="empty-message">No archived tasks</div>
                        {% endif %}
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
"""

PRINCIPLES_TEMPLATE = """
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Productivity Principles</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f5f5f5;
            padding: 20px;
            max-width: 800px;
            margin: 0 auto;
        }
        h1 {
            color: #333;
            text-align: center;
        }
        .principle {
            background: white;
            padding: 15px;
            margin: 10px 0;
            border-radius: 8px;
            border-left: 4px solid #4CAF50;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .back-btn {
            background: #4CAF50;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            text-decoration: none;
            display: inline-block;
            margin-bottom: 20px;
        }
        .back-btn:hover {
            background: #45a049;
        }
    </style>
</head>
<body>
    <a href="/" class="back-btn">← Back to Matrix</a>
    <h1>Productivity Principles</h1>
    <div class="principle">1. Concentrate on the most important tasks and ignore/delegate the rest (see #4).</div>
    <div class="principle">2. Aim to be excellent enough to take advantage of the 80/20 rule, but do it correctly.</div>
    <div class="principle">3. There is no such thing as "finding time to accomplish things." You must consciously choose not to do anything else in order to make time.</div>
    <div class="principle">4. To categorize tasks, use the Eisenhower decision matrix.<br>
    - If it's urgent and vital, do it right away.<br>
    - Schedule a time to accomplish it if it is not urgent and critical.<br>
    - If it is both urgent and unimportant, attempt to assign it.<br>
    - Ignore it if it isn't urgent or vital.</div>
    <div class="principle">5. Make a fake deadline and pretend it's real to deal with procrastination.</div>
    <div class="principle">6. Work to slowly minimize wasted time through weekly reflections and periodically tracking your time.</div>
    <div class="principle">7. You can't accomplish anything worthwhile alone. Write thank you notes to people who help you.</div>
    <div class="principle">8. Find your creative time, the few hours a day you are most productive, and guard it with your life (no meetings).</div>
    <div class="principle">9. Set an hourly rate for your time and value it more than your money. Try to outsource most things below your rate.</div>
    <div class="principle">10. It takes time to recover after you're interrupted. Checking your phone for 3 minutes takes roughly 10 minutes away from you since you need to refocus.</div>
</body>
</html>
"""

@app.route('/')
def index():
    tasks = get_tasks()
    archived_tasks = get_archived_tasks()
    today = datetime.now().strftime('%Y-%m-%d')
    
    # Helper function to check if task is overdue
    def is_overdue(task):
        if task.get('due_date') and not task.get('completed') and not task.get('archived'):
            return task['due_date'] < today
        return False
    
    return render_template_string(HTML_TEMPLATE, tasks=tasks, archived_tasks=archived_tasks, today=today, is_overdue=is_overdue)

@app.route('/add', methods=['POST'])
def add_task():
    title = request.form.get('title', '').strip()
    description = request.form.get('description', '').strip()
    quadrant = request.form.get('quadrant', '').strip()
    due_date = request.form.get('due_date', '').strip()
    
    # Validation
    if not title or not quadrant:
        return redirect(url_for('index'))
    
    if quadrant not in ['do', 'decide', 'delegate', 'delete']:
        return redirect(url_for('index'))
    
    # Truncate if needed
    title = title[:100]
    description = description[:500]
    
    # Insert into database
    conn = sqlite3.connect(DB_FILE)
    cursor = conn.cursor()
    cursor.execute('''
        INSERT INTO tasks (title, description, quadrant, due_date, created_at, original_quadrant)
        VALUES (?, ?, ?, ?, ?, ?)
    ''', (title, description, quadrant, due_date if due_date else None, datetime.now().isoformat(), quadrant))
    conn.commit()
    conn.close()
    
    return redirect(url_for('index'))

@app.route('/edit', methods=['POST'])
def edit_task():
    task_id = request.form.get('task_id', '').strip()
    title = request.form.get('title', '').strip()
    description = request.form.get('description', '').strip()
    quadrant = request.form.get('quadrant', '').strip()
    due_date = request.form.get('due_date', '').strip()
    
    # Validation
    if not task_id or not title or not quadrant:
        return redirect(url_for('index'))
    
    if quadrant not in ['do', 'decide', 'delegate', 'delete']:
        return redirect(url_for('index'))
    
    # Truncate if needed
    title = title[:100]
    description = description[:500]
    
    # Update in database
    conn = sqlite3.connect(DB_FILE)
    cursor = conn.cursor()
    cursor.execute('''
        UPDATE tasks 
        SET title = ?, description = ?, quadrant = ?, due_date = ?
        WHERE id = ? AND archived = 0
    ''', (title, description, quadrant, due_date if due_date else None, task_id))
    conn.commit()
    conn.close()
    
    return redirect(url_for('index'))

@app.route('/principles')
def principles():
    return render_template_string(PRINCIPLES_TEMPLATE)

@app.route('/move/<int:task_id>/<quadrant>', methods=['POST'])
def move_task(task_id, quadrant):
    if quadrant not in ['do', 'decide', 'delegate', 'delete']:
        return redirect(url_for('index'))
    
    conn = sqlite3.connect(DB_FILE)
    cursor = conn.cursor()
    cursor.execute('UPDATE tasks SET quadrant = ? WHERE id = ? AND archived = 0', (quadrant, task_id))
    conn.commit()
    conn.close()
    
    return redirect(url_for('index'))

@app.route('/complete/<int:task_id>', methods=['POST'])
def complete_task(task_id):
    conn = sqlite3.connect(DB_FILE)
    cursor = conn.cursor()
    cursor.execute('''
        UPDATE tasks SET completed = 1, completed_at = ? 
        WHERE id = ? AND archived = 0 AND completed = 0
    ''', (datetime.now().isoformat(), task_id))
    conn.commit()
    conn.close()
    
    return redirect(url_for('index'))

@app.route('/archive/<int:task_id>', methods=['POST'])
def archive_task(task_id):
    conn = sqlite3.connect(DB_FILE)
    cursor = conn.cursor()
    # Get current quadrant before archiving
    cursor.execute('SELECT quadrant FROM tasks WHERE id = ?', (task_id,))
    result = cursor.fetchone()
    if result:
        current_quadrant = result[0]
        cursor.execute('''
            UPDATE tasks SET archived = 1, archived_at = ?, original_quadrant = ?
            WHERE id = ?
        ''', (datetime.now().isoformat(), current_quadrant, task_id))
        conn.commit()
    conn.close()
    
    return redirect(url_for('index'))

if __name__ == '__main__':
    print("\n" + "="*60)
    print("  Eisenhower Matrix Task Manager")
    print("="*60)
    print("\nStarting server...")
    print("Open your browser and go to: http://localhost:5000")
    print("\nPress Ctrl+C to stop the server\n")
    app.run(debug=True, host='0.0.0.0', port=5000)
