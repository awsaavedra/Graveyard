# The most basic jamstack demo site
Javascript APIs and Markup (JAM) stack

1. Static Assets
2. Git based
3. CDN hosting
4. Atomic deploys
5. Cache Invalidation
6. Forms: Netlify forms or getform.io
7. Lunrjs - search
8. Google Cloud Platform

## JAMstack principles
    1. Static markup
    2. CDN hosting
    3. Automated, Git-based Atomic deploys
    4. Server abstraction via Javascript APIs

# Technologies used:
1. Comments (disqus.com)
2. Netlify forms (simple contact forms)
3. CDN - Netlify deploy
4. Host scaling
    a. Amazon AWS

# Markup requirements (Static site generators):
## Static site generators
    1. Hugo (picked)
    2. Jekyll
    3. Next.js
    4. Gatsby.js
    ...
a. Content sites
b. Simple to setup and manage 

https://jamstack.org/generators/



## Frontend Javascript frameworks:
    1. VueJs
    2. React
    3. Angular
    ...
a. User Roles
b. WordPress-like
c. Fully featured

## Headless Content Management Systems
Think headless browser, just means a lack of a user interface
    - We get an interface of the CMS itself. A CMS is not responsible for the rendering itself
    - Types of CMS: API driven CMS or a git based CMS
        - An API driven CMS (SaaS) divorces itself from the site. Hugo requires physical content driven files so API driven is harder for Hugo
        - Git based, newly generated content is pushed into the site.
            - usually self-hosted
            - All data is in repo 
            - third party authentication
            - integrates with static site
            - inherits JAMStack hosting, security, etc.

            

    1. Netlify CMS
    2. Ghost
    3. Strapi
    4. Directus

Properties 

    a. Content light
    b. Web App
    c. Progessive Web Applications - PWA 
    


## SDLC Tools
    1. ZEIT Now
    2. Bitbucket workflows
    3. GitLab
    4. GitHub Actions
    5. AWS Amplify Console

## Other tech services to potentially use:

        1. firebase
        2. Stripe
        3. Algolia
        4. Paypal
        5. formspree
        6. trustpilot
