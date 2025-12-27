#!/usr/bin/env python3
"""
Eisenhower Matrix Task Manager - Command Line Interface
A simple, self-contained task management tool using the Eisenhower Decision Matrix
No external dependencies - uses only Python standard library
"""

import sqlite3
import os
import sys
from datetime import datetime
from typing import List, Dict, Optional

# Try to import colorama for colored output, but gracefully fallback if not available
try:
    from colorama import init, Fore, Style
    init(autoreset=True)
    COLORS_AVAILABLE = True
except ImportError:
    COLORS_AVAILABLE = False
    # Define dummy color constants
    class Fore:
        GREEN = BLUE = RED = LIGHTBLACK_EX = WHITE = ''
    class Style:
        BRIGHT = RESET_ALL = ''

# Database file location
DB_FILE = os.path.join(os.path.dirname(os.path.abspath(__file__)), 'tasks.db')

# Quadrant definitions
QUADRANTS = {
    'do': {'name': 'DO', 'desc': 'Urgent & Important', 'color': Fore.GREEN},
    'decide': {'name': 'DECIDE', 'desc': 'Not Urgent & Important', 'color': Fore.BLUE},
    'delegate': {'name': 'DELEGATE', 'desc': 'Urgent & Not Important', 'color': Fore.RED},
    'delete': {'name': 'DELETE', 'desc': 'Neither', 'color': Fore.LIGHTBLACK_EX}
}

# Productivity principles
PRINCIPLES = [
    "1. Concentrate on the most important tasks and ignore/delegate the rest (see #4).",
    "2. Aim to be excellent enough to take advantage of the 80/20 rule, but do it correctly.",
    "3. There is no such thing as 'finding time to accomplish things.' You must consciously choose not to do anything else in order to make time.",
    "4. To categorize tasks, use the Eisenhower decision matrix:\n   - If it's urgent and vital, do it right away.\n   - Schedule a time to accomplish it if it is not urgent and critical.\n   - If it is both urgent and unimportant, attempt to assign it.\n   - Ignore it if it isn't urgent or vital.",
    "5. Make a fake deadline and pretend it's real to deal with procrastination.",
    "6. Work to slowly minimize wasted time through weekly reflections and periodically tracking your time.",
    "7. You can't accomplish anything worthwhile alone. Write thank you notes to people who help you.",
    "8. Find your creative time, the few hours a day you are most productive, and guard it with your life (no meetings).",
    "9. Set an hourly rate for your time and value it more than your money. Try to outsource most things below your rate.",
    "10. It takes time to recover after you're interrupted. Checking your phone for 3 minutes takes roughly 10 minutes away from you since you need to refocus."
]


class Database:
    """Handle all database operations"""
    
    def __init__(self, db_path: str = DB_FILE):
        self.db_path = db_path
        self.init_db()
    
    def init_db(self):
        """Initialize database with tasks table"""
        conn = sqlite3.connect(self.db_path)
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
        conn.close()
    
    def add_task(self, title: str, description: str, quadrant: str, due_date: str) -> int:
        """Add a new task"""
        conn = sqlite3.connect(self.db_path)
        cursor = conn.cursor()
        cursor.execute('''
            INSERT INTO tasks (title, description, quadrant, due_date, created_at, original_quadrant)
            VALUES (?, ?, ?, ?, ?, ?)
        ''', (title, description, quadrant, due_date, datetime.now().isoformat(), quadrant))
        task_id = cursor.lastrowid
        conn.commit()
        conn.close()
        return task_id
    
    def get_tasks(self, include_archived: bool = False) -> List[Dict]:
        """Get all tasks"""
        conn = sqlite3.connect(self.db_path)
        conn.row_factory = sqlite3.Row
        cursor = conn.cursor()
        
        if include_archived:
            cursor.execute('SELECT * FROM tasks WHERE archived = 1 ORDER BY archived_at DESC')
        else:
            cursor.execute('SELECT * FROM tasks WHERE archived = 0 ORDER BY created_at ASC')
        
        tasks = [dict(row) for row in cursor.fetchall()]
        conn.close()
        return tasks
    
    def get_task(self, task_id: int) -> Optional[Dict]:
        """Get a single task by ID"""
        conn = sqlite3.connect(self.db_path)
        conn.row_factory = sqlite3.Row
        cursor = conn.cursor()
        cursor.execute('SELECT * FROM tasks WHERE id = ?', (task_id,))
        row = cursor.fetchone()
        conn.close()
        return dict(row) if row else None
    
    def update_task_quadrant(self, task_id: int, quadrant: str) -> bool:
        """Move task to different quadrant"""
        conn = sqlite3.connect(self.db_path)
        cursor = conn.cursor()
        cursor.execute('UPDATE tasks SET quadrant = ? WHERE id = ? AND archived = 0', (quadrant, task_id))
        success = cursor.rowcount > 0
        conn.commit()
        conn.close()
        return success
    
    def complete_task(self, task_id: int) -> bool:
        """Mark task as completed"""
        conn = sqlite3.connect(self.db_path)
        cursor = conn.cursor()
        cursor.execute('''
            UPDATE tasks SET completed = 1, completed_at = ? 
            WHERE id = ? AND archived = 0 AND completed = 0
        ''', (datetime.now().isoformat(), task_id))
        success = cursor.rowcount > 0
        conn.commit()
        conn.close()
        return success
    
    def archive_task(self, task_id: int) -> bool:
        """Archive a task"""
        task = self.get_task(task_id)
        if not task or task['archived']:
            return False
        
        conn = sqlite3.connect(self.db_path)
        cursor = conn.cursor()
        cursor.execute('''
            UPDATE tasks SET archived = 1, archived_at = ?, original_quadrant = ?
            WHERE id = ?
        ''', (datetime.now().isoformat(), task['quadrant'], task_id))
        success = cursor.rowcount > 0
        conn.commit()
        conn.close()
        return success
    
    def count_tasks(self) -> int:
        """Count total non-archived tasks"""
        conn = sqlite3.connect(self.db_path)
        cursor = conn.cursor()
        cursor.execute('SELECT COUNT(*) FROM tasks WHERE archived = 0')
        count = cursor.fetchone()[0]
        conn.close()
        return count


class MatrixDisplay:
    """Handle ASCII art display of the matrix"""
    
    @staticmethod
    def clear_screen():
        """Clear the terminal screen"""
        os.system('cls' if os.name == 'nt' else 'clear')
    
    @staticmethod
    def wrap_text(text: str, width: int) -> List[str]:
        """Wrap text to specified width"""
        if len(text) <= width:
            return [text]
        
        lines = []
        while text:
            if len(text) <= width:
                lines.append(text)
                break
            
            # Find last space within width
            split_pos = text.rfind(' ', 0, width)
            if split_pos == -1:
                split_pos = width
            
            lines.append(text[:split_pos])
            text = text[split_pos:].lstrip()
        
        return lines
    
    @staticmethod
    def format_task(task: Dict, width: int = 26) -> List[str]:
        """Format a task as lines of text"""
        lines = []
        
        # Task number and completion status
        status = '[x]' if task['completed'] else '[ ]'
        title_prefix = f"{task['id']}. {status} "
        
        # Wrap title
        title_width = width - len(title_prefix)
        title_lines = MatrixDisplay.wrap_text(task['title'], title_width)
        
        lines.append(title_prefix + title_lines[0])
        for line in title_lines[1:]:
            lines.append(' ' * len(title_prefix) + line)
        
        # Due date
        if task['due_date']:
            lines.append(f"   Due: {task['due_date']}")
        
        return lines
    
    @staticmethod
    def display_matrix(tasks: List[Dict]):
        """Display the Eisenhower matrix with tasks"""
        MatrixDisplay.clear_screen()
        
        # Organize tasks by quadrant
        tasks_by_quadrant = {'do': [], 'decide': [], 'delegate': [], 'delete': []}
        for task in tasks:
            if not task['archived']:
                tasks_by_quadrant[task['quadrant']].append(task)
        
        # Print header
        print("\n" + "="*70)
        print(f"{'EISENHOWER MATRIX TASK MANAGER':^70}")
        print("="*70 + "\n")
        
        # Box drawing characters
        top_left, top_right = '╔', '╗'
        bottom_left, bottom_right = '╚', '╝'
        horizontal, vertical = '═', '║'
        t_down, t_up = '╦', '╩'
        cross = '╬'
        t_right, t_left = '╠', '╣'
        
        width = 32
        
        # Top border
        print(f"{top_left}{horizontal * width}{t_down}{horizontal * width}{top_right}")
        
        # Top row (DO and DECIDE)
        for quadrant_pair in [('do', 'decide')]:
            for quad_key in quadrant_pair:
                quad_info = QUADRANTS[quad_key]
                color = quad_info['color'] if COLORS_AVAILABLE else ''
                reset = Style.RESET_ALL if COLORS_AVAILABLE else ''
                header = f"{quad_info['name']} ({quad_info['desc']})"
                print(f"{vertical} {color}{header[:width-2].ljust(width-2)}{reset}", end='')
            print(vertical)
            
            # Print tasks or empty message
            max_lines = max(
                sum(len(MatrixDisplay.format_task(t, width-4)) + 1 for t in tasks_by_quadrant['do']),
                sum(len(MatrixDisplay.format_task(t, width-4)) + 1 for t in tasks_by_quadrant['decide'])
            )
            max_lines = max(max_lines, 5)  # Minimum height
            
            for line_num in range(max_lines):
                for quad_key in quadrant_pair:
                    tasks_list = tasks_by_quadrant[quad_key]
                    current_line = 0
                    content = ''
                    
                    for task in tasks_list:
                        task_lines = MatrixDisplay.format_task(task, width-4)
                        if current_line <= line_num < current_line + len(task_lines):
                            content = task_lines[line_num - current_line]
                            break
                        current_line += len(task_lines) + 1
                    
                    if not content and line_num == 0 and not tasks_list:
                        content = '(No tasks)'
                    
                    print(f"{vertical} {content.ljust(width-2)}", end='')
                print(vertical)
        
        # Middle border
        print(f"{t_right}{horizontal * width}{cross}{horizontal * width}{t_left}")
        
        # Bottom row (DELEGATE and DELETE)
        for quadrant_pair in [('delegate', 'delete')]:
            for quad_key in quadrant_pair:
                quad_info = QUADRANTS[quad_key]
                color = quad_info['color'] if COLORS_AVAILABLE else ''
                reset = Style.RESET_ALL if COLORS_AVAILABLE else ''
                header = f"{quad_info['name']} ({quad_info['desc']})"
                print(f"{vertical} {color}{header[:width-2].ljust(width-2)}{reset}", end='')
            print(vertical)
            
            # Print tasks or empty message
            max_lines = max(
                sum(len(MatrixDisplay.format_task(t, width-4)) + 1 for t in tasks_by_quadrant['delegate']),
                sum(len(MatrixDisplay.format_task(t, width-4)) + 1 for t in tasks_by_quadrant['delete'])
            )
            max_lines = max(max_lines, 5)  # Minimum height
            
            for line_num in range(max_lines):
                for quad_key in quadrant_pair:
                    tasks_list = tasks_by_quadrant[quad_key]
                    current_line = 0
                    content = ''
                    
                    for task in tasks_list:
                        task_lines = MatrixDisplay.format_task(task, width-4)
                        if current_line <= line_num < current_line + len(task_lines):
                            content = task_lines[line_num - current_line]
                            break
                        current_line += len(task_lines) + 1
                    
                    if not content and line_num == 0 and not tasks_list:
                        content = '(No tasks)'
                    
                    print(f"{vertical} {content.ljust(width-2)}", end='')
                print(vertical)
        
        # Bottom border
        print(f"{bottom_left}{horizontal * width}{t_up}{horizontal * width}{bottom_right}\n")
        
        # Command help
        print("Commands: add | move | complete | archive | view | principles | help | quit")


class CommandParser:
    """Parse and execute user commands"""
    
    def __init__(self, db: Database):
        self.db = db
        self.display = MatrixDisplay()
    
    def run(self):
        """Main command loop"""
        while True:
            tasks = self.db.get_tasks()
            self.display.display_matrix(tasks)
            
            try:
                command = input("\n> ").strip().lower()
                
                if not command:
                    continue
                
                if command in ['quit', 'exit', 'q']:
                    print("\nGoodbye!")
                    break
                
                self.process_command(command)
                
            except KeyboardInterrupt:
                print("\n\nGoodbye!")
                break
            except Exception as e:
                print(f"\nError: {e}")
                input("\nPress Enter to continue...")
    
    def process_command(self, command: str):
        """Process a single command"""
        parts = command.split(maxsplit=1)
        cmd = parts[0]
        args = parts[1] if len(parts) > 1 else ''
        
        if cmd in ['add', 'a']:
            self.cmd_add(args)
        elif cmd in ['move', 'm']:
            self.cmd_move(args)
        elif cmd in ['complete', 'c', 'done']:
            self.cmd_complete(args)
        elif cmd in ['archive', 'ar']:
            self.cmd_archive(args)
        elif cmd in ['view', 'v']:
            self.cmd_view(args)
        elif cmd in ['principles', 'p']:
            self.cmd_principles()
        elif cmd in ['help', 'h', '?']:
            self.cmd_help()
        else:
            print(f"\nUnknown command: {cmd}")
            print("Type 'help' for available commands")
            input("\nPress Enter to continue...")
    
    def cmd_add(self, args: str):
        """Add a new task"""
        print("\n--- Add New Task ---")
        
        # Check task limit
        if self.db.count_tasks() >= 100:
            print("Error: Maximum task limit (100) reached. Archive some tasks first.")
            input("\nPress Enter to continue...")
            return
        
        # Get task details
        title = input("Title (required): ").strip()
        if not title:
            print("Error: Title is required")
            input("\nPress Enter to continue...")
            return
        
        if len(title) > 100:
            title = title[:100]
            print(f"Title truncated to 100 characters: {title}")
        
        description = input("Description (optional): ").strip()
        if len(description) > 500:
            description = description[:500]
            print("Description truncated to 500 characters")
        
        # Select quadrant
        print("\nQuadrants:")
        for key, info in QUADRANTS.items():
            print(f"  {key}: {info['name']} ({info['desc']})")
        
        quadrant = input("Quadrant (do/decide/delegate/delete): ").strip().lower()
        if quadrant not in QUADRANTS:
            print(f"Error: Invalid quadrant '{quadrant}'")
            input("\nPress Enter to continue...")
            return
        
        # Get due date
        due_date = input("Due date (YYYY-MM-DD, optional): ").strip()
        if due_date:
            try:
                datetime.strptime(due_date, '%Y-%m-%d')
            except ValueError:
                print("Error: Invalid date format. Use YYYY-MM-DD")
                input("\nPress Enter to continue...")
                return
        
        # Add task
        task_id = self.db.add_task(title, description, quadrant, due_date)
        print(f"\n✓ Task #{task_id} added to {QUADRANTS[quadrant]['name']} quadrant")
        input("\nPress Enter to continue...")
    
    def cmd_move(self, args: str):
        """Move a task to a different quadrant"""
        try:
            # Parse: "move 1 to decide" or "1 decide"
            parts = args.replace(' to ', ' ').split()
            if len(parts) < 2:
                print("Usage: move <task_id> to <quadrant>")
                input("\nPress Enter to continue...")
                return
            
            task_id = int(parts[0])
            quadrant = parts[1].lower()
            
            if quadrant not in QUADRANTS:
                print(f"Error: Invalid quadrant '{quadrant}'")
                input("\nPress Enter to continue...")
                return
            
            task = self.db.get_task(task_id)
            if not task:
                print(f"Error: Task #{task_id} not found")
                input("\nPress Enter to continue...")
                return
            
            if task['archived']:
                print(f"Error: Cannot move archived task")
                input("\nPress Enter to continue...")
                return
            
            if task['quadrant'] == quadrant:
                print(f"Task is already in {QUADRANTS[quadrant]['name']} quadrant")
                input("\nPress Enter to continue...")
                return
            
            self.db.update_task_quadrant(task_id, quadrant)
            print(f"\n✓ Task #{task_id} moved to {QUADRANTS[quadrant]['name']} quadrant")
            input("\nPress Enter to continue...")
            
        except (ValueError, IndexError):
            print("Usage: move <task_id> to <quadrant>")
            print("Example: move 1 to decide")
            input("\nPress Enter to continue...")
    
    def cmd_complete(self, args: str):
        """Mark a task as completed"""
        try:
            task_id = int(args.strip())
            
            task = self.db.get_task(task_id)
            if not task:
                print(f"Error: Task #{task_id} not found")
                input("\nPress Enter to continue...")
                return
            
            if task['completed']:
                print(f"Task #{task_id} is already completed")
                input("\nPress Enter to continue...")
                return
            
            if task['archived']:
                print(f"Error: Cannot complete archived task")
                input("\nPress Enter to continue...")
                return
            
            self.db.complete_task(task_id)
            print(f"\n✓ Task #{task_id} marked as completed")
            input("\nPress Enter to continue...")
            
        except ValueError:
            print("Usage: complete <task_id>")
            print("Example: complete 1")
            input("\nPress Enter to continue...")
    
    def cmd_archive(self, args: str):
        """Archive a task"""
        try:
            task_id = int(args.strip())
            
            task = self.db.get_task(task_id)
            if not task:
                print(f"Error: Task #{task_id} not found")
                input("\nPress Enter to continue...")
                return
            
            if task['archived']:
                print(f"Task #{task_id} is already archived")
                input("\nPress Enter to continue...")
                return
            
            self.db.archive_task(task_id)
            print(f"\n✓ Task #{task_id} archived")
            input("\nPress Enter to continue...")
            
        except ValueError:
            print("Usage: archive <task_id>")
            print("Example: archive 1")
            input("\nPress Enter to continue...")
    
    def cmd_view(self, args: str):
        """View archived tasks"""
        if args.strip().lower() in ['archived', 'archive', 'ar']:
            self.display.clear_screen()
            print("\n=== Archived Tasks ===\n")
            
            tasks = self.db.get_tasks(include_archived=True)
            if not tasks:
                print("No archived tasks")
            else:
                for task in tasks:
                    status = '[x]' if task['completed'] else '[ ]'
                    print(f"#{task['id']} {status} {task['title']}")
                    print(f"   Original quadrant: {QUADRANTS[task['original_quadrant']]['name']}")
                    if task['due_date']:
                        print(f"   Due date: {task['due_date']}")
                    if task['archived_at']:
                        archived_date = task['archived_at'][:10]
                        print(f"   Archived: {archived_date}")
                    print()
            
            input("\nPress Enter to continue...")
        else:
            print("Usage: view archived")
            input("\nPress Enter to continue...")
    
    def cmd_principles(self):
        """Display productivity principles"""
        self.display.clear_screen()
        print("\n" + "="*70)
        print(f"{'PRODUCTIVITY PRINCIPLES':^70}")
        print("="*70 + "\n")
        
        for principle in PRINCIPLES:
            print(principle)
            print()
        
        input("\nPress Enter to continue...")
    
    def cmd_help(self):
        """Display help information"""
        self.display.clear_screen()
        print("\n=== Available Commands ===\n")
        print("add (a)           - Add a new task")
        print("move (m)          - Move task to different quadrant")
        print("                    Example: move 1 to decide")
        print("complete (c)      - Mark task as completed")
        print("                    Example: complete 1")
        print("archive (ar)      - Archive a task")
        print("                    Example: archive 1")
        print("view archived     - View archived tasks")
        print("principles (p)    - Display productivity principles")
        print("help (h)          - Show this help")
        print("quit (q)          - Exit the application")
        print()
        input("Press Enter to continue...")


def main():
    """Main entry point"""
    print("\nInitializing Eisenhower Matrix Task Manager...")
    
    # Initialize database
    db = Database()
    
    # Add a test task if database is empty
    if db.count_tasks() == 0:
        db.add_task(
            "Test Card", 
            "Click arrows to move me between quadrants!",
            "do",
            "2025-12-27"
        )
    
    # Start command loop
    parser = CommandParser(db)
    parser.run()


if __name__ == '__main__':
    main()
