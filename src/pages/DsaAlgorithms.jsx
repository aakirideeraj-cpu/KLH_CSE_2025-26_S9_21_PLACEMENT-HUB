import { Hash, Braces, GitBranch, ListOrdered, SearchCode, Lightbulb } from "lucide-react";
import { FlowHorizontal } from "../components/FlowDiagram";

const algorithms = [
  {
    icon: Hash,
    name: "HashMap",
    purpose: "Fast lookup of student and company records.",
    complexity: "Average O(1)",
    extra: "Keys: Student ID / Company ID → Record",
  },
  {
    icon: Braces,
    name: "HashSet",
    purpose: "Store and compare unique skills.",
    complexity: "Average O(1) lookup",
    extra: "Used for skill-set intersection during matching",
  },
  {
    icon: GitBranch,
    name: "Binary Search Tree",
    purpose: "Organize students according to CGPA.",
    complexity: "O(log n) average",
    extra: "Operations: Insert · Search · Traversal",
  },
  {
    icon: ListOrdered,
    name: "Priority Queue",
    purpose: "Rank eligible candidates according to placement priority.",
    complexity: "O(log n) push / pop",
    extra: "Max-heap keyed by candidate match score",
  },
  {
    icon: SearchCode,
    name: "Rabin-Karp",
    purpose: "Search patterns across the placement document corpus.",
    complexity: "O(n + m) average",
    extra: "Rolling hash across TCS, Infosys, Accenture, Wipro, Amazon docs",
  },
];

export default function DsaAlgorithms() {
  return (
    <div className="animate-fadeUp space-y-8">
      <div>
        <h1 className="font-display text-[24px] font-bold text-navy-900 sm:text-[26px]">
          DSA Algorithms
        </h1>
        <p className="mt-1.5 text-[14px] text-slate-500">
          How data structures and algorithms power every feature in Placement Hub.
        </p>
      </div>

      <div className="grid grid-cols-1 gap-5 sm:grid-cols-2 xl:grid-cols-3">
        {algorithms.map((a) => (
          <div
            key={a.name}
            className="rounded-2xl border border-slate-200 bg-white p-5 shadow-card transition-all hover:-translate-y-0.5 hover:shadow-cardHover"
          >
            <div className="flex h-11 w-11 items-center justify-center rounded-xl bg-navy-50 text-navy-700">
              <a.icon className="h-5 w-5" strokeWidth={2} />
            </div>
            <p className="mt-4 font-display text-[16.5px] font-bold text-navy-900">{a.name}</p>
            <p className="mt-2 text-[13px] leading-relaxed text-slate-500">{a.purpose}</p>
            <div className="mt-4 flex items-center justify-between border-t border-slate-100 pt-3">
              <span className="text-[11px] font-medium uppercase tracking-wide text-slate-400">
                Complexity
              </span>
              <span className="rounded-full bg-accent-50 px-2.5 py-1 font-mono text-[12px] font-semibold text-accent-700">
                {a.complexity}
              </span>
            </div>
            <p className="mt-2.5 text-[11.5px] text-slate-400">{a.extra}</p>
          </div>
        ))}
      </div>

      {/* Rabin-Karp corpus flow */}
      <div className="rounded-2xl border border-slate-200 bg-white p-5 shadow-card sm:p-6">
        <p className="mb-1 font-display text-[16px] font-bold text-navy-900">
          Document Search Pipeline
        </p>
        <p className="mb-5 text-[13px] text-slate-500">
          How Rabin-Karp scans the placement document corpus for a keyword.
        </p>
        <FlowHorizontal
          steps={[
            { label: "Text Corpus", sub: "5 documents" },
            { label: "Rolling Hash", sub: "Per window" },
            { label: "Pattern Comparison", sub: "On hash match" },
            { label: "Matching Documents", sub: "Ranked results" },
          ]}
        />
      </div>

      {/* Why DSA */}
      <div className="rounded-2xl border border-navy-800 bg-navy-900 p-6 sm:p-8">
        <div className="flex items-start gap-3">
          <div className="flex h-10 w-10 shrink-0 items-center justify-center rounded-xl bg-accent-500/20 text-accent-400">
            <Lightbulb className="h-5 w-5" />
          </div>
          <div>
            <p className="font-display text-[18px] font-bold text-white">Why DSA?</p>
            <p className="mt-2.5 max-w-2xl text-[14px] leading-relaxed text-navy-200">
              Placement Hub is designed to demonstrate practical applications of fundamental
              data structures and algorithms, rather than being just a CRUD application.
              Every screen in this prototype maps to a specific algorithmic idea: hashing
              for lookup, tree structures for ordered data, heaps for prioritization, and
              string-matching algorithms for search — the same building blocks used in
              real-world placement and recruitment systems at scale.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}
