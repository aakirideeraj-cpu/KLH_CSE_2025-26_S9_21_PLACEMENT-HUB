import {
  Target,
  CheckCircle2,
  Coffee,
  Layers,
  Database,
  Plug,
  Hash,
  Braces,
  GitBranch,
  ListOrdered,
  SearchCode,
} from "lucide-react";

const objectives = [
  "Efficient student lookup",
  "Skill-based matching",
  "CGPA-based eligibility",
  "Candidate ranking",
  "Placement document searching",
  "Practical application of DSA",
];

const tech = [
  { icon: Coffee, name: "Java", desc: "Core application logic" },
  { icon: Layers, name: "JavaFX", desc: "Desktop application UI (final build)" },
  { icon: Database, name: "MySQL", desc: "Relational data storage" },
  { icon: Plug, name: "JDBC", desc: "Database connectivity layer" },
];

const dsa = [
  { icon: Hash, name: "HashMap" },
  { icon: Braces, name: "HashSet" },
  { icon: GitBranch, name: "Binary Search Tree" },
  { icon: ListOrdered, name: "Priority Queue" },
  { icon: SearchCode, name: "Rabin-Karp" },
];

export default function AboutProject() {
  return (
    <div className="animate-fadeUp space-y-8">
      <div className="flex items-center gap-3">
        <div className="flex h-11 w-11 items-center justify-center rounded-xl bg-navy-900">
          <Target className="h-5 w-5 text-white" />
        </div>
        <div>
          <h1 className="font-display text-[24px] font-bold text-navy-900 sm:text-[26px]">
            About Placement Hub
          </h1>
          <p className="text-[13.5px] text-slate-500">College DSA academic project</p>
        </div>
      </div>

      <div className="rounded-2xl border border-slate-200 bg-white p-6 shadow-card sm:p-8">
        <p className="max-w-3xl text-[14.5px] leading-relaxed text-slate-600">
          Placement Hub is a DSA-based student placement matching system that connects
          student profiles with company requirements using efficient data structures and
          algorithms. This prototype focuses on the user experience of the platform; the
          underlying matching, ranking, and search logic will be implemented in Java and
          backed by a MySQL database, connected through JDBC.
        </p>
      </div>

      {/* Objectives */}
      <div>
        <h2 className="mb-4 font-display text-[17px] font-bold text-navy-900">
          Project Objectives
        </h2>
        <div className="grid grid-cols-1 gap-3 sm:grid-cols-2">
          {objectives.map((o) => (
            <div
              key={o}
              className="flex items-center gap-3 rounded-xl border border-slate-200 bg-white p-4 shadow-card"
            >
              <CheckCircle2 className="h-[18px] w-[18px] shrink-0 text-emerald-500" />
              <span className="text-[13.5px] font-medium text-navy-800">{o}</span>
            </div>
          ))}
        </div>
      </div>

      {/* Technology */}
      <div>
        <h2 className="mb-4 font-display text-[17px] font-bold text-navy-900">Technology</h2>
        <div className="grid grid-cols-2 gap-3 sm:grid-cols-4">
          {tech.map((t) => (
            <div
              key={t.name}
              className="rounded-xl border border-slate-200 bg-white p-4 text-center shadow-card"
            >
              <div className="mx-auto flex h-10 w-10 items-center justify-center rounded-lg bg-navy-50 text-navy-700">
                <t.icon className="h-5 w-5" />
              </div>
              <p className="mt-2.5 text-[13.5px] font-bold text-navy-900">{t.name}</p>
              <p className="mt-0.5 text-[11px] leading-snug text-slate-400">{t.desc}</p>
            </div>
          ))}
        </div>
      </div>

      {/* DSA */}
      <div>
        <h2 className="mb-4 font-display text-[17px] font-bold text-navy-900">
          Data Structures &amp; Algorithms
        </h2>
        <div className="grid grid-cols-2 gap-3 sm:grid-cols-5">
          {dsa.map((d) => (
            <div
              key={d.name}
              className="rounded-xl border border-slate-200 bg-white p-4 text-center shadow-card"
            >
              <div className="mx-auto flex h-10 w-10 items-center justify-center rounded-lg bg-accent-50 text-accent-600">
                <d.icon className="h-5 w-5" />
              </div>
              <p className="mt-2.5 text-[12.5px] font-bold text-navy-900">{d.name}</p>
            </div>
          ))}
        </div>
      </div>

      {/* Architecture roadmap */}
      <div className="rounded-2xl border border-slate-200 bg-white p-6 shadow-card sm:p-8">
        <h2 className="mb-1 font-display text-[17px] font-bold text-navy-900">
          From Prototype to Full System
        </h2>
        <p className="mb-5 text-[13px] text-slate-500">
          This React interface is the frontend prototype only. It is designed so the same
          screens can later be connected to a real Java backend.
        </p>
        <div className="flex flex-col items-center gap-1.5">
          {[
            "React / Prototype",
            "Java Application",
            "DSA Algorithms",
            "JDBC",
            "MySQL",
          ].map((step, i, arr) => (
            <div key={step} className="flex w-full flex-col items-center">
              <div className="w-full max-w-xs rounded-xl border border-slate-200 bg-slate-50 py-2.5 text-center text-[13px] font-semibold text-navy-800">
                {step}
              </div>
              {i < arr.length - 1 && <div className="my-1 h-4 w-px bg-slate-300" />}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
