# Placement Hub — Smart Student Placement Matching System

A frontend prototype for a college DSA project. Built with **React + Vite + Tailwind CSS**.

This is a **UI prototype only** — there is no backend, database, or real algorithm
implementation wired in yet. All data lives in `src/data/*.js` as static mock data.
The one exception is the **Pattern Search** page, which runs a small real
Rabin-Karp implementation *in the browser* purely to make that screen feel alive;
it is not connected to the eventual Java/MySQL/JDBC backend.

## Getting started

```bash
npm install
npm run dev
```

Then open the printed local URL (usually `http://localhost:5173`).

To build a production bundle:

```bash
npm run build
npm run preview
```

## Project structure

```
src/
├── components/   Reusable UI pieces (Sidebar, Topbar, cards, panels, flow diagrams...)
├── pages/        One file per route (Landing, Dashboard, Students, ...)
├── data/         Mock students, companies, documents + the demo Rabin-Karp util
├── layouts/      AppLayout.jsx — sidebar + topbar shell used by every inner page
└── App.jsx       Route definitions
```

## Planned architecture (not implemented yet)

```
React / Prototype  ->  Java Application  ->  DSA Algorithms  ->  JDBC  ->  MySQL
```

| DSA Concept        | Where it will be used                        |
|---------------------|-----------------------------------------------|
| HashMap             | Fast student / company lookup by ID          |
| HashSet             | Unique skill sets per student                |
| Binary Search Tree  | Organizing / searching students by CGPA      |
| Priority Queue      | Ranking eligible candidates for a drive      |
| Rabin-Karp          | Pattern search across placement documents    |

## Notes

- No paid services, Firebase, Supabase, or external backend are used.
- All numbers (250+ students, 42 companies, 1,240 matches, etc.) are placeholder
  figures for the prototype.
