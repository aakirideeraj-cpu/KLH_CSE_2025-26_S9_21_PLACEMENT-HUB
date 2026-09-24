import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Building2, GraduationCap, Users, ArrowRight } from "lucide-react";
import SkillTag from "../components/SkillTag";
import { companies } from "../data/companies";
import { useToast } from "../components/ToastProvider";

const statusStyles = {
  Open: "bg-emerald-50 text-emerald-700 border-emerald-200",
  Upcoming: "bg-amber-50 text-amber-700 border-amber-200",
};

export default function Companies() {
  const [detailFor, setDetailFor] = useState(null);
  const { showToast } = useToast();
  const navigate = useNavigate();

  return (
    <div className="animate-fadeUp space-y-6">
      <div>
        <h1 className="font-display text-[24px] font-bold text-navy-900 sm:text-[26px]">
          Companies
        </h1>
        <p className="mt-1.5 text-[14px] text-slate-500">
          Explore placement opportunities and eligibility requirements.
        </p>
      </div>

      <div className="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-3">
        {companies.map((c) => (
          <div
            key={c.id}
            className="flex flex-col rounded-2xl border border-slate-200 bg-white p-5 shadow-card transition-all hover:-translate-y-0.5 hover:shadow-cardHover"
          >
            <div className="flex items-start justify-between">
              <div className="flex items-center gap-3">
                <div className="flex h-11 w-11 items-center justify-center rounded-xl bg-navy-900 text-[13px] font-bold text-white">
                  {c.name.slice(0, 2).toUpperCase()}
                </div>
                <div>
                  <p className="font-display text-[15.5px] font-bold text-navy-900">{c.name}</p>
                  <p className="text-[12px] text-slate-400">{c.role}</p>
                </div>
              </div>
              <span
                className={`rounded-full border px-2.5 py-1 text-[11px] font-semibold ${statusStyles[c.status]}`}
              >
                {c.status}
              </span>
            </div>

            <div className="mt-4 flex items-center gap-1.5 text-[13px] text-slate-600">
              <GraduationCap className="h-4 w-4 text-slate-400" />
              CGPA Cutoff: <span className="font-semibold text-navy-800">{c.cgpaCutoff.toFixed(2)}</span>
            </div>

            <div className="mt-3">
              <p className="mb-1.5 text-[11.5px] font-medium text-slate-400">Required Skills</p>
              <div className="flex flex-wrap gap-1.5">
                {c.skills.map((s) => (
                  <SkillTag key={s} skill={s} />
                ))}
              </div>
            </div>

            <div className="mt-4 flex items-center gap-4 text-[12px] text-slate-500">
              <span className="flex items-center gap-1">
                <Users className="h-3.5 w-3.5" /> {c.openings} openings
              </span>
              <span>{c.package}</span>
            </div>

            <div className="mt-5 flex gap-2 border-t border-slate-100 pt-4">
              <button
                onClick={() => setDetailFor(c)}
                className="focus-ring flex-1 rounded-lg border border-slate-200 px-3 py-2 text-[12.5px] font-semibold text-navy-700 transition-colors hover:bg-slate-50"
              >
                View Details
              </button>
              <button
                onClick={() => {
                  showToast(`Ranking eligible candidates for ${c.name}...`, "success");
                  navigate("/ranking");
                }}
                className="focus-ring flex flex-1 items-center justify-center gap-1 rounded-lg bg-navy-900 px-3 py-2 text-[12.5px] font-semibold text-white transition-colors hover:bg-navy-800"
              >
                Find Candidates
                <ArrowRight className="h-3.5 w-3.5" />
              </button>
            </div>
          </div>
        ))}
      </div>

      {detailFor && (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4">
          <div
            className="absolute inset-0 bg-navy-950/40 backdrop-blur-[2px]"
            onClick={() => setDetailFor(null)}
          />
          <div className="relative w-full max-w-md animate-fadeUp rounded-2xl bg-white p-6 shadow-2xl">
            <div className="flex items-center gap-3">
              <div className="flex h-12 w-12 items-center justify-center rounded-xl bg-navy-900 text-[14px] font-bold text-white">
                {detailFor.name.slice(0, 2).toUpperCase()}
              </div>
              <div>
                <p className="font-display text-[17px] font-bold text-navy-900">
                  {detailFor.name}
                </p>
                <p className="text-[12.5px] text-slate-400">{detailFor.role}</p>
              </div>
            </div>

            <div className="mt-5 grid grid-cols-2 gap-3">
              <DetailTile label="CGPA Cutoff" value={detailFor.cgpaCutoff.toFixed(2)} />
              <DetailTile label="Openings" value={detailFor.openings} />
              <DetailTile label="Package" value={detailFor.package} />
              <DetailTile label="Drive Date" value={detailFor.drive} />
            </div>

            <div className="mt-5">
              <p className="mb-1.5 text-[11.5px] font-medium text-slate-400">Required Skills</p>
              <div className="flex flex-wrap gap-1.5">
                {detailFor.skills.map((s) => (
                  <SkillTag key={s} skill={s} size="md" />
                ))}
              </div>
            </div>

            <div className="mt-5 flex items-center justify-between border-t border-slate-100 pt-4">
              <Building2 className="h-4 w-4 text-slate-300" />
              <button
                onClick={() => setDetailFor(null)}
                className="focus-ring rounded-lg bg-slate-100 px-4 py-2 text-[12.5px] font-semibold text-navy-700 hover:bg-slate-200"
              >
                Close
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

function DetailTile({ label, value }) {
  return (
    <div className="rounded-xl border border-slate-200 bg-slate-50 p-3">
      <p className="text-[11px] font-medium uppercase tracking-wide text-slate-400">{label}</p>
      <p className="mt-1 text-[13.5px] font-semibold text-navy-800">{value}</p>
    </div>
  );
}
