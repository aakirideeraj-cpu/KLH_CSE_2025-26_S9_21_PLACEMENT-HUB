import { X, GraduationCap, Sparkles, Building2, Gauge } from "lucide-react";
import SkillTag from "./SkillTag";

export default function StudentDetailPanel({ student, onClose }) {
  if (!student) return null;

  const readiness =
    student.matchScore >= 85 ? "High" : student.matchScore >= 65 ? "Moderate" : "Low";
  const readinessStyle =
    readiness === "High"
      ? "bg-emerald-50 text-emerald-700 border-emerald-200"
      : readiness === "Moderate"
      ? "bg-amber-50 text-amber-700 border-amber-200"
      : "bg-rose-50 text-rose-700 border-rose-200";

  return (
    <div className="fixed inset-0 z-50 flex justify-end">
      <div className="absolute inset-0 bg-navy-950/40 backdrop-blur-[2px]" onClick={onClose} />
      <div className="relative flex h-full w-full max-w-md animate-fadeUp flex-col bg-white shadow-2xl">
        <div className="flex items-start justify-between border-b border-slate-100 px-6 py-5">
          <div className="flex items-center gap-3">
            <div className="flex h-12 w-12 items-center justify-center rounded-full bg-navy-900 text-[15px] font-bold text-white">
              {student.name
                .split(" ")
                .map((w) => w[0])
                .slice(0, 2)
                .join("")}
            </div>
            <div>
              <p className="font-display text-[16px] font-bold text-navy-900">{student.name}</p>
              <p className="text-[12.5px] text-slate-400">
                {student.id} · {student.branch}
              </p>
            </div>
          </div>
          <button
            onClick={onClose}
            className="focus-ring flex h-8 w-8 items-center justify-center rounded-lg text-slate-400 hover:bg-slate-100 hover:text-navy-700"
            aria-label="Close panel"
          >
            <X className="h-[18px] w-[18px]" />
          </button>
        </div>

        <div className="flex-1 overflow-y-auto px-6 py-6">
          <div className="grid grid-cols-2 gap-3">
            <InfoTile icon={GraduationCap} label="CGPA" value={student.cgpa.toFixed(2)} />
            <InfoTile
              icon={Building2}
              label="Eligible Companies"
              value={student.eligibleCompanies}
            />
          </div>

          <div className="mt-6">
            <p className="mb-2.5 flex items-center gap-1.5 text-[12.5px] font-semibold uppercase tracking-wide text-slate-400">
              <Sparkles className="h-3.5 w-3.5" /> Skills
            </p>
            <div className="flex flex-wrap gap-2">
              {student.skills.map((s) => (
                <SkillTag key={s} skill={s} size="md" />
              ))}
            </div>
          </div>

          <div className="mt-6">
            <p className="mb-2 flex items-center gap-1.5 text-[12.5px] font-semibold uppercase tracking-wide text-slate-400">
              <Gauge className="h-3.5 w-3.5" /> Matching Score
            </p>
            <div className="h-2.5 w-full overflow-hidden rounded-full bg-slate-100">
              <div
                className="h-full rounded-full bg-gradient-to-r from-accent-500 to-accent-600 transition-all"
                style={{ width: `${student.matchScore}%` }}
              />
            </div>
            <p className="mt-1.5 text-[12.5px] text-slate-500">
              {student.matchScore}% average match across eligible companies
            </p>
          </div>

          <div className="mt-6 rounded-xl border border-slate-200 bg-slate-50 px-4 py-3.5">
            <div className="flex items-center justify-between">
              <p className="text-[13px] font-semibold text-navy-800">Placement Readiness</p>
              <span className={`rounded-full border px-2.5 py-1 text-[11px] font-semibold ${readinessStyle}`}>
                {readiness}
              </span>
            </div>
            <p className="mt-1.5 text-[12.5px] leading-relaxed text-slate-500">
              Based on CGPA percentile (Binary Search Tree ordering), skill overlap
              (HashSet comparison), and eligibility across active company drives.
            </p>
          </div>

          <div className="mt-6 rounded-xl border border-dashed border-slate-300 px-4 py-3.5">
            <p className="text-[11.5px] leading-relaxed text-slate-400">
              This profile is prototype mock data. In the final system, this panel is
              populated by a Java service querying MySQL via JDBC.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}

function InfoTile({ icon: Icon, label, value }) {
  return (
    <div className="rounded-xl border border-slate-200 bg-white p-3.5">
      <div className="flex items-center gap-1.5 text-slate-400">
        <Icon className="h-3.5 w-3.5" />
        <span className="text-[11px] font-medium uppercase tracking-wide">{label}</span>
      </div>
      <p className="mt-1.5 font-display text-[19px] font-bold text-navy-900">{value}</p>
    </div>
  );
}
