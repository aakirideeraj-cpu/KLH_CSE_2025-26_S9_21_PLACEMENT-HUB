import { useMemo, useState } from "react";
import { ChevronDown, ListOrdered, Trophy } from "lucide-react";
import { FlowHorizontal } from "../components/FlowDiagram";
import { students } from "../data/students";
import { companies } from "../data/companies";

function rankCandidates(company) {
  return students
    .filter((s) => s.cgpa >= company.cgpaCutoff)
    .map((s) => {
      const matchedSkills = company.skills.filter((sk) => s.skills.includes(sk));
      const skillRatio = matchedSkills.length / company.skills.length;
      const cgpaBonus = Math.min((s.cgpa - company.cgpaCutoff) * 4, 12);
      const score = Math.min(Math.round(skillRatio * 88 + cgpaBonus), 100);
      return { ...s, matchedSkills, score };
    })
    .sort((a, b) => b.score - a.score || b.cgpa - a.cgpa);
}

const medalStyles = [
  "bg-amber-100 text-amber-700 border-amber-200",
  "bg-slate-100 text-slate-600 border-slate-200",
  "bg-orange-100 text-orange-700 border-orange-200",
];

export default function CandidateRanking() {
  const [companyId, setCompanyId] = useState(companies[0].id);
  const company = companies.find((c) => c.id === companyId);
  const ranked = useMemo(() => rankCandidates(company), [company]);

  return (
    <div className="animate-fadeUp space-y-6">
      <div>
        <h1 className="font-display text-[24px] font-bold text-navy-900 sm:text-[26px]">
          Candidate Ranking
        </h1>
        <p className="mt-1.5 text-[14px] text-slate-500">
          Prioritize eligible candidates for a placement drive.
        </p>
      </div>

      <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
        <div className="relative w-full sm:w-64">
          <select
            value={companyId}
            onChange={(e) => setCompanyId(e.target.value)}
            className="focus-ring w-full appearance-none rounded-xl border border-slate-200 bg-white px-4 py-2.5 pr-9 text-[14px] font-semibold text-navy-800 shadow-card"
          >
            {companies.map((c) => (
              <option key={c.id} value={c.id}>
                {c.name}
              </option>
            ))}
          </select>
          <ChevronDown className="pointer-events-none absolute right-3.5 top-1/2 h-4 w-4 -translate-y-1/2 text-slate-400" />
        </div>

        <div className="flex items-center gap-2 rounded-full border border-accent-200 bg-accent-50 px-3.5 py-1.5 text-[12px] font-semibold text-accent-700">
          <ListOrdered className="h-3.5 w-3.5" />
          Powered by Priority Queue
        </div>
      </div>

      {/* Criteria */}
      <div className="rounded-2xl border border-slate-200 bg-white p-5 shadow-card">
        <p className="mb-3 text-[12.5px] font-semibold uppercase tracking-wide text-slate-400">
          Placement Criteria — {company.name}
        </p>
        <div className="flex flex-wrap items-center gap-3 text-[13.5px]">
          <span className="rounded-lg bg-navy-50 px-3 py-1.5 font-semibold text-navy-700">
            CGPA ≥ {company.cgpaCutoff.toFixed(2)}
          </span>
          <span className="rounded-lg bg-navy-50 px-3 py-1.5 font-semibold text-navy-700">
            Skills: {company.skills.join(", ")}
          </span>
          <span className="text-slate-400">·</span>
          <span className="text-slate-500">{ranked.length} eligible candidates found</span>
        </div>
      </div>

      {/* Ranked list */}
      <div className="rounded-2xl border border-slate-200 bg-white p-5 shadow-card">
        <div className="mb-4 flex items-center gap-2">
          <Trophy className="h-4 w-4 text-amber-500" />
          <p className="font-display text-[16px] font-bold text-navy-900">Ranked Candidates</p>
        </div>

        <div className="space-y-2.5">
          {ranked.map((s, i) => (
            <div
              key={s.id}
              className="flex flex-col gap-3 rounded-xl border border-slate-200 p-4 transition-all hover:shadow-card sm:flex-row sm:items-center sm:justify-between"
            >
              <div className="flex items-center gap-3.5">
                <span
                  className={`flex h-9 w-9 shrink-0 items-center justify-center rounded-full border text-[13px] font-bold ${
                    medalStyles[i] || "border-slate-200 bg-slate-50 text-slate-500"
                  }`}
                >
                  #{i + 1}
                </span>
                <div>
                  <p className="text-[14px] font-semibold text-navy-800">{s.name}</p>
                  <p className="text-[12px] text-slate-400">
                    CGPA {s.cgpa.toFixed(2)} · {s.skills.join(" • ")}
                  </p>
                </div>
              </div>

              <div className="flex items-center gap-3 sm:w-48">
                <div className="h-1.5 flex-1 overflow-hidden rounded-full bg-slate-100">
                  <div
                    className="h-full rounded-full bg-gradient-to-r from-accent-500 to-emerald-500"
                    style={{ width: `${s.score}%` }}
                  />
                </div>
                <span className="w-14 shrink-0 text-right font-display text-[15px] font-bold text-navy-900">
                  {s.score}%
                </span>
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* How ranking works */}
      <div className="rounded-2xl border border-slate-200 bg-white p-5 shadow-card sm:p-6">
        <p className="mb-1 font-display text-[16px] font-bold text-navy-900">
          How Priority Queue Ranking Works
        </p>
        <p className="mb-5 text-[13px] text-slate-500">
          Each eligible candidate is pushed into a max-heap keyed by match score, so the
          highest-priority candidate is always retrieved first.
        </p>
        <FlowHorizontal
          steps={[
            { label: "Filter Eligible", sub: "CGPA + skills" },
            { label: "Compute Score", sub: "Per candidate" },
            { label: "Push to Heap", sub: "Priority Queue" },
            { label: "Pop Max", sub: "Repeat" },
            { label: "Ranked Output", sub: "#1 → #N" },
          ]}
        />
      </div>
    </div>
  );
}
