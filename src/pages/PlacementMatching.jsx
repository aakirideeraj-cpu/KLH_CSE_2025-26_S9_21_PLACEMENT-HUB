import { useMemo, useState } from "react";
import { CheckCircle2, XCircle, ChevronDown, GitMerge } from "lucide-react";
import SkillTag from "../components/SkillTag";
import { FlowVertical } from "../components/FlowDiagram";
import { students } from "../data/students";
import { companies } from "../data/companies";

function computeMatches(student) {
  return companies
    .map((c) => {
      const cgpaOk = student.cgpa >= c.cgpaCutoff;
      const matchedSkills = c.skills.filter((sk) => student.skills.includes(sk));
      const skillRatio = matchedSkills.length / c.skills.length;
      let score = Math.round(skillRatio * 80 + (cgpaOk ? 20 : 0));
      if (!cgpaOk) score = Math.round(score * 0.4);
      return {
        ...c,
        cgpaOk,
        matchedSkills,
        score: Math.min(score, 99),
      };
    })
    .sort((a, b) => b.score - a.score);
}

export default function PlacementMatching() {
  const [studentId, setStudentId] = useState(students[0].id);
  const student = students.find((s) => s.id === studentId);
  const matches = useMemo(() => computeMatches(student), [student]);
  const eligibleMatches = matches.filter((m) => m.cgpaOk);

  return (
    <div className="animate-fadeUp space-y-8">
      <div>
        <h1 className="font-display text-[24px] font-bold text-navy-900 sm:text-[26px]">
          Placement Matching Engine
        </h1>
        <p className="mt-1.5 text-[14px] text-slate-500">
          Match students with companies using CGPA and skill requirements.
        </p>
      </div>

      <div className="grid grid-cols-1 gap-6 lg:grid-cols-5">
        {/* LEFT: Select Student */}
        <div className="lg:col-span-2">
          <div className="rounded-2xl border border-slate-200 bg-white p-5 shadow-card">
            <p className="mb-3 text-[12.5px] font-semibold uppercase tracking-wide text-slate-400">
              Select Student
            </p>
            <div className="relative">
              <select
                value={studentId}
                onChange={(e) => setStudentId(e.target.value)}
                className="focus-ring w-full appearance-none rounded-xl border border-slate-200 bg-slate-50 px-4 py-3 pr-9 text-[14px] font-semibold text-navy-800"
              >
                {students.map((s) => (
                  <option key={s.id} value={s.id}>
                    {s.name}
                  </option>
                ))}
              </select>
              <ChevronDown className="pointer-events-none absolute right-3.5 top-1/2 h-4 w-4 -translate-y-1/2 text-slate-400" />
            </div>

            <div className="mt-5 flex items-center gap-3">
              <div className="flex h-12 w-12 items-center justify-center rounded-full bg-navy-900 text-[14px] font-bold text-white">
                {student.name
                  .split(" ")
                  .map((w) => w[0])
                  .slice(0, 2)
                  .join("")}
              </div>
              <div>
                <p className="font-display text-[16px] font-bold text-navy-900">{student.name}</p>
                <p className="text-[12px] text-slate-400">{student.id} · {student.branch}</p>
              </div>
            </div>

            <div className="mt-5 grid grid-cols-2 gap-3">
              <div className="rounded-xl border border-slate-200 bg-slate-50 p-3.5">
                <p className="text-[11px] font-medium uppercase tracking-wide text-slate-400">
                  CGPA
                </p>
                <p className="mt-1 font-display text-[20px] font-bold text-navy-900">
                  {student.cgpa.toFixed(2)}
                </p>
              </div>
              <div className="rounded-xl border border-slate-200 bg-slate-50 p-3.5">
                <p className="text-[11px] font-medium uppercase tracking-wide text-slate-400">
                  Eligible Companies
                </p>
                <p className="mt-1 font-display text-[20px] font-bold text-navy-900">
                  {eligibleMatches.length}
                </p>
              </div>
            </div>

            <div className="mt-5">
              <p className="mb-2 text-[11.5px] font-medium text-slate-400">Skills</p>
              <div className="flex flex-wrap gap-2">
                {student.skills.map((s) => (
                  <SkillTag key={s} skill={s} size="md" />
                ))}
              </div>
            </div>
          </div>

          {/* Matching Process */}
          <div className="mt-6 rounded-2xl border border-slate-200 bg-white p-5 shadow-card">
            <p className="mb-4 text-[12.5px] font-semibold uppercase tracking-wide text-slate-400">
              Matching Process
            </p>
            <FlowVertical
              steps={[
                { label: "Student Profile", sub: "CGPA + skill set" },
                { label: "CGPA Check", sub: "Binary Search Tree lookup" },
                { label: "Skill Matching", sub: "HashSet intersection" },
                { label: "DSA Matching Engine", sub: "Score & combine" },
                { label: "Eligible Companies", sub: "Ranked output" },
              ]}
            />
          </div>
        </div>

        {/* RIGHT: Recommended Companies */}
        <div className="lg:col-span-3">
          <div className="rounded-2xl border border-slate-200 bg-white p-5 shadow-card">
            <div className="mb-4 flex items-center justify-between">
              <p className="font-display text-[16px] font-bold text-navy-900">
                Recommended Companies
              </p>
              <span className="flex items-center gap-1.5 text-[12px] text-slate-400">
                <GitMerge className="h-3.5 w-3.5" /> Live match preview
              </span>
            </div>

            <div className="space-y-3">
              {matches.map((m) => (
                <div
                  key={m.id}
                  className={`rounded-xl border p-4 transition-all hover:shadow-card ${
                    m.cgpaOk ? "border-slate-200 bg-white" : "border-slate-100 bg-slate-50/60"
                  }`}
                >
                  <div className="flex items-center justify-between">
                    <div className="flex items-center gap-3">
                      <div className="flex h-10 w-10 items-center justify-center rounded-lg bg-navy-900 text-[12px] font-bold text-white">
                        {m.name.slice(0, 2).toUpperCase()}
                      </div>
                      <div>
                        <p className="text-[14px] font-semibold text-navy-800">{m.name}</p>
                        <p className="text-[11.5px] text-slate-400">{m.role}</p>
                      </div>
                    </div>
                    <div className="text-right">
                      <p
                        className={`font-display text-[20px] font-bold ${
                          m.score >= 90
                            ? "text-emerald-600"
                            : m.score >= 70
                            ? "text-accent-600"
                            : "text-slate-400"
                        }`}
                      >
                        {m.score}%
                      </p>
                      <p className="text-[10.5px] text-slate-400">Match</p>
                    </div>
                  </div>

                  <div className="mt-3 h-1.5 w-full overflow-hidden rounded-full bg-slate-100">
                    <div
                      className={`h-full rounded-full transition-all ${
                        m.score >= 90
                          ? "bg-emerald-500"
                          : m.score >= 70
                          ? "bg-accent-500"
                          : "bg-slate-300"
                      }`}
                      style={{ width: `${m.score}%` }}
                    />
                  </div>

                  <div className="mt-3 flex flex-wrap items-center gap-x-4 gap-y-1.5 text-[12.5px]">
                    <span
                      className={`flex items-center gap-1 font-medium ${
                        m.cgpaOk ? "text-emerald-600" : "text-rose-500"
                      }`}
                    >
                      {m.cgpaOk ? (
                        <CheckCircle2 className="h-3.5 w-3.5" />
                      ) : (
                        <XCircle className="h-3.5 w-3.5" />
                      )}
                      CGPA {m.cgpaOk ? "Eligible" : `Requires ${m.cgpaCutoff.toFixed(2)}`}
                    </span>
                    {m.skills.map((sk) => {
                      const matched = m.matchedSkills.includes(sk);
                      return (
                        <span
                          key={sk}
                          className={`flex items-center gap-1 font-medium ${
                            matched ? "text-emerald-600" : "text-slate-400"
                          }`}
                        >
                          {matched ? (
                            <CheckCircle2 className="h-3.5 w-3.5" />
                          ) : (
                            <XCircle className="h-3.5 w-3.5" />
                          )}
                          {sk}
                        </span>
                      );
                    })}
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
