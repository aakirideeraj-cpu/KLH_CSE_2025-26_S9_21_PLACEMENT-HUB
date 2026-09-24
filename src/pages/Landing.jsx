import { Link } from "react-router-dom";
import {
  Target,
  ArrowRight,
  Users,
  Building2,
  GitMerge,
  Trophy,
  Hash,
  Braces,
  GitBranch,
  ListOrdered,
  SearchCode,
  ArrowDown,
} from "lucide-react";

const stats = [
  { label: "Students", value: "250+" },
  { label: "Companies", value: "42" },
  { label: "Matches", value: "1,240" },
  { label: "DSA Algorithms", value: "5" },
];

const dsaCards = [
  {
    icon: Hash,
    name: "HashMap",
    desc: "Fast Student Lookup",
  },
  {
    icon: Braces,
    name: "HashSet",
    desc: "Unique Skill Management",
  },
  {
    icon: GitBranch,
    name: "Binary Search Tree",
    desc: "CGPA Based Organization",
  },
  {
    icon: ListOrdered,
    name: "Priority Queue",
    desc: "Candidate Ranking",
  },
  {
    icon: SearchCode,
    name: "Rabin-Karp",
    desc: "Placement Document Search",
  },
];

export default function Landing() {
  return (
    <div className="min-h-screen bg-white">
      {/* Top nav */}
      <header className="border-b border-slate-100">
        <div className="mx-auto flex h-16 max-w-6xl items-center justify-between px-5 sm:px-8">
          <div className="flex items-center gap-2.5">
            <div className="flex h-8 w-8 items-center justify-center rounded-lg bg-navy-900">
              <Target className="h-[18px] w-[18px] text-white" />
            </div>
            <span className="font-display text-[15px] font-bold text-navy-900">
              Placement Hub
            </span>
          </div>
          <Link
            to="/dashboard"
            className="focus-ring rounded-lg bg-navy-900 px-4 py-2 text-[13px] font-semibold text-white transition-colors hover:bg-navy-800"
          >
            Open Dashboard
          </Link>
        </div>
      </header>

      {/* Hero */}
      <section className="relative overflow-hidden">
        <div className="pointer-events-none absolute inset-x-0 top-0 h-[520px] bg-gradient-to-b from-accent-50/70 via-white to-white" />
        <div className="relative mx-auto max-w-6xl px-5 pb-20 pt-16 sm:px-8 sm:pt-24">
          <div className="mx-auto max-w-3xl text-center">
            <div className="mx-auto mb-6 inline-flex items-center gap-2 rounded-full border border-slate-200 bg-white px-3.5 py-1.5 text-[12px] font-medium text-slate-500 shadow-sm">
              <span className="h-1.5 w-1.5 rounded-full bg-emerald-500" />
              College DSA Project · Java · MySQL · JDBC
            </div>
            <h1 className="font-display text-[38px] font-bold leading-[1.12] tracking-tight text-navy-900 sm:text-[52px]">
              Find the Right Placement.
              <br />
              Build the Right Future.
            </h1>
            <p className="mx-auto mt-5 max-w-xl text-[15.5px] leading-relaxed text-slate-500 sm:text-[16.5px]">
              Placement Hub connects student skills and academic performance with
              suitable placement opportunities using efficient data structures and
              algorithms.
            </p>
            <div className="mt-8 flex flex-col items-center justify-center gap-3 sm:flex-row">
              <Link
                to="/dashboard"
                className="focus-ring group flex w-full items-center justify-center gap-2 rounded-xl bg-accent-600 px-6 py-3 text-[14px] font-semibold text-white shadow-card transition-all hover:bg-accent-700 hover:shadow-cardHover sm:w-auto"
              >
                Explore Placement Hub
                <ArrowRight className="h-4 w-4 transition-transform group-hover:translate-x-0.5" />
              </Link>
              <Link
                to="/dsa-algorithms"
                className="focus-ring flex w-full items-center justify-center gap-2 rounded-xl border border-slate-200 bg-white px-6 py-3 text-[14px] font-semibold text-navy-800 transition-colors hover:border-slate-300 hover:bg-slate-50 sm:w-auto"
              >
                View DSA Architecture
              </Link>
            </div>
          </div>

          {/* Hero visual: pipeline illustration */}
          <div className="relative mx-auto mt-16 max-w-3xl">
            <div className="rounded-2xl border border-slate-200 bg-white p-6 shadow-cardHover sm:p-8">
              <div className="flex flex-col items-center gap-1.5">
                <PipelineNode
                  icon={Users}
                  title="Students"
                  detail="Skills + CGPA"
                />
                <ArrowDown className="h-4 w-4 text-slate-300" />
                <PipelineNode
                  icon={GitMerge}
                  title="Matching Engine"
                  detail="HashMap · BST · HashSet"
                  emphasis
                />
                <ArrowDown className="h-4 w-4 text-slate-300" />
                <PipelineNode
                  icon={Building2}
                  title="Companies"
                  detail="Requirements"
                />
                <ArrowDown className="h-4 w-4 text-slate-300" />
                <PipelineNode
                  icon={Trophy}
                  title="Ranked Candidates"
                  detail="Priority Queue"
                />
              </div>
            </div>

            {/* Floating stat cards */}
            <FloatingStat
              className="-left-6 top-6 hidden sm:flex lg:-left-12"
              value="250+"
              label="Students"
            />
            <FloatingStat
              className="-right-6 top-24 hidden sm:flex lg:-right-14"
              value="42"
              label="Companies"
            />
            <FloatingStat
              className="-left-8 bottom-20 hidden sm:flex lg:-left-16"
              value="1,240"
              label="Matches"
            />
            <FloatingStat
              className="-right-4 bottom-2 hidden sm:flex lg:-right-10"
              value="5"
              label="DSA Algorithms"
            />
          </div>

          {/* Mobile stat row */}
          <div className="mt-10 grid grid-cols-2 gap-3 sm:hidden">
            {stats.map((s) => (
              <div
                key={s.label}
                className="rounded-xl border border-slate-200 bg-white p-4 text-center shadow-card"
              >
                <p className="font-display text-xl font-bold text-navy-900">{s.value}</p>
                <p className="text-[11.5px] text-slate-500">{s.label}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Built Around DSA */}
      <section className="border-t border-slate-100 bg-slate-50/60">
        <div className="mx-auto max-w-6xl px-5 py-20 sm:px-8">
          <div className="mx-auto max-w-xl text-center">
            <h2 className="font-display text-[28px] font-bold text-navy-900 sm:text-[32px]">
              Built Around DSA
            </h2>
            <p className="mt-3 text-[14.5px] leading-relaxed text-slate-500">
              Every core feature in Placement Hub is powered by a specific data
              structure or algorithm — not a generic database query.
            </p>
          </div>

          <div className="mt-12 grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-5">
            {dsaCards.map((card) => (
              <div
                key={card.name}
                className="group rounded-2xl border border-slate-200 bg-white p-5 shadow-card transition-all hover:-translate-y-0.5 hover:shadow-cardHover"
              >
                <div className="flex h-10 w-10 items-center justify-center rounded-xl bg-navy-50 text-navy-700 transition-colors group-hover:bg-accent-50 group-hover:text-accent-600">
                  <card.icon className="h-5 w-5" strokeWidth={2} />
                </div>
                <p className="mt-4 font-display text-[15px] font-bold text-navy-900">
                  {card.name}
                </p>
                <p className="mt-1 text-[13px] leading-snug text-slate-500">{card.desc}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Footer CTA */}
      <section className="border-t border-slate-100">
        <div className="mx-auto flex max-w-6xl flex-col items-center gap-5 px-5 py-16 text-center sm:px-8">
          <p className="font-display text-[22px] font-bold text-navy-900">
            Ready to explore the platform?
          </p>
          <Link
            to="/dashboard"
            className="focus-ring flex items-center gap-2 rounded-xl bg-navy-900 px-6 py-3 text-[14px] font-semibold text-white transition-colors hover:bg-navy-800"
          >
            Go to Dashboard
            <ArrowRight className="h-4 w-4" />
          </Link>
        </div>
        <div className="border-t border-slate-100 py-6 text-center text-[12px] text-slate-400">
          Placement Hub — DSA Academic Project Prototype · Frontend only, not connected to Java/MySQL yet
        </div>
      </section>
    </div>
  );
}

function PipelineNode({ icon: Icon, title, detail, emphasis }) {
  return (
    <div
      className={`flex w-full max-w-xs items-center gap-3 rounded-xl border px-4 py-3 ${
        emphasis
          ? "border-accent-200 bg-accent-50"
          : "border-slate-200 bg-white"
      }`}
    >
      <div
        className={`flex h-9 w-9 shrink-0 items-center justify-center rounded-lg ${
          emphasis ? "bg-accent-600 text-white" : "bg-navy-50 text-navy-700"
        }`}
      >
        <Icon className="h-[18px] w-[18px]" strokeWidth={2} />
      </div>
      <div className="text-left">
        <p className="text-[13.5px] font-semibold text-navy-900">{title}</p>
        <p className="text-[11.5px] text-slate-500">{detail}</p>
      </div>
    </div>
  );
}

function FloatingStat({ value, label, className }) {
  return (
    <div
      className={`absolute w-[104px] animate-fadeUp flex-col items-center rounded-xl border border-slate-200 bg-white px-3 py-2.5 text-center shadow-cardHover ${className}`}
    >
      <p className="font-display text-base font-bold text-navy-900">{value}</p>
      <p className="text-[10.5px] text-slate-500">{label}</p>
    </div>
  );
}
