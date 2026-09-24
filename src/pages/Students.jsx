import { useMemo, useState } from "react";
import { Search, SlidersHorizontal, ChevronRight, Users } from "lucide-react";
import SkillTag from "../components/SkillTag";
import EmptyState from "../components/EmptyState";
import StudentDetailPanel from "../components/StudentDetailPanel";
import { students, allSkills } from "../data/students";

const cgpaFilters = [
  { label: "All CGPA", value: "all" },
  { label: "9.0 and above", value: "9" },
  { label: "8.0 – 8.99", value: "8" },
  { label: "7.0 – 7.99", value: "7" },
  { label: "Below 7.0", value: "below7" },
];

const statusFilters = ["All", "Eligible", "Not Eligible"];

export default function Students() {
  const [query, setQuery] = useState("");
  const [cgpaFilter, setCgpaFilter] = useState("all");
  const [skillFilter, setSkillFilter] = useState("All Skills");
  const [statusFilter, setStatusFilter] = useState("All");
  const [selected, setSelected] = useState(null);

  const filtered = useMemo(() => {
    return students.filter((s) => {
      const q = query.trim().toLowerCase();
      const matchesQuery =
        !q ||
        s.name.toLowerCase().includes(q) ||
        s.skills.some((sk) => sk.toLowerCase().includes(q));

      const matchesCgpa =
        cgpaFilter === "all" ||
        (cgpaFilter === "9" && s.cgpa >= 9) ||
        (cgpaFilter === "8" && s.cgpa >= 8 && s.cgpa < 9) ||
        (cgpaFilter === "7" && s.cgpa >= 7 && s.cgpa < 8) ||
        (cgpaFilter === "below7" && s.cgpa < 7);

      const matchesSkill = skillFilter === "All Skills" || s.skills.includes(skillFilter);
      const matchesStatus = statusFilter === "All" || s.status === statusFilter;

      return matchesQuery && matchesCgpa && matchesSkill && matchesStatus;
    });
  }, [query, cgpaFilter, skillFilter, statusFilter]);

  return (
    <div className="animate-fadeUp space-y-6">
      <div>
        <h1 className="font-display text-[24px] font-bold text-navy-900 sm:text-[26px]">
          Students
        </h1>
        <p className="mt-1.5 text-[14px] text-slate-500">
          Explore student profiles and placement readiness.
        </p>
      </div>

      {/* Search + filters */}
      <div className="rounded-2xl border border-slate-200 bg-white p-4 shadow-card">
        <div className="relative">
          <Search className="pointer-events-none absolute left-3.5 top-1/2 h-4 w-4 -translate-y-1/2 text-slate-400" />
          <input
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            type="text"
            placeholder="Search by name or skill..."
            className="focus-ring w-full rounded-xl border border-slate-200 bg-slate-50 py-2.5 pl-10 pr-3 text-[13.5px] text-navy-800 placeholder:text-slate-400 focus:bg-white"
          />
        </div>

        <div className="mt-3 flex flex-wrap items-center gap-2">
          <span className="mr-1 flex items-center gap-1 text-[12px] font-medium text-slate-400">
            <SlidersHorizontal className="h-3.5 w-3.5" /> Filters:
          </span>

          <select
            value={cgpaFilter}
            onChange={(e) => setCgpaFilter(e.target.value)}
            className="focus-ring rounded-lg border border-slate-200 bg-white px-3 py-1.5 text-[12.5px] font-medium text-navy-700"
          >
            {cgpaFilters.map((f) => (
              <option key={f.value} value={f.value}>
                {f.label}
              </option>
            ))}
          </select>

          <select
            value={skillFilter}
            onChange={(e) => setSkillFilter(e.target.value)}
            className="focus-ring rounded-lg border border-slate-200 bg-white px-3 py-1.5 text-[12.5px] font-medium text-navy-700"
          >
            <option>All Skills</option>
            {allSkills.map((s) => (
              <option key={s}>{s}</option>
            ))}
          </select>

          <div className="flex items-center gap-1 rounded-lg border border-slate-200 bg-white p-1">
            {statusFilters.map((s) => (
              <button
                key={s}
                onClick={() => setStatusFilter(s)}
                className={`focus-ring rounded-md px-2.5 py-1 text-[12px] font-medium transition-colors ${
                  statusFilter === s
                    ? "bg-navy-900 text-white"
                    : "text-slate-500 hover:bg-slate-100"
                }`}
              >
                {s}
              </button>
            ))}
          </div>

          <span className="ml-auto text-[12px] text-slate-400">
            {filtered.length} of {students.length} students
          </span>
        </div>
      </div>

      {/* Table */}
      {filtered.length === 0 ? (
        <EmptyState
          icon={Users}
          title="No students match your filters"
          description="Try adjusting the search term or clearing a filter."
        />
      ) : (
        <div className="overflow-hidden rounded-2xl border border-slate-200 bg-white shadow-card">
          <div className="overflow-x-auto">
            <table className="w-full min-w-[760px] text-left text-[13px]">
              <thead>
                <tr className="border-b border-slate-100 text-[11.5px] uppercase tracking-wide text-slate-400">
                  <th className="px-5 py-3 font-medium">Student ID</th>
                  <th className="px-5 py-3 font-medium">Name</th>
                  <th className="px-5 py-3 font-medium">CGPA</th>
                  <th className="px-5 py-3 font-medium">Skills</th>
                  <th className="px-5 py-3 font-medium">Eligible Companies</th>
                  <th className="px-5 py-3 font-medium">Status</th>
                  <th className="px-5 py-3" />
                </tr>
              </thead>
              <tbody className="divide-y divide-slate-100">
                {filtered.map((s) => (
                  <tr
                    key={s.id}
                    onClick={() => setSelected(s)}
                    className="cursor-pointer transition-colors hover:bg-slate-50"
                  >
                    <td className="px-5 py-3.5 font-mono text-[12px] text-slate-400">{s.id}</td>
                    <td className="px-5 py-3.5 font-semibold text-navy-800">{s.name}</td>
                    <td className="px-5 py-3.5 text-slate-600">{s.cgpa.toFixed(2)}</td>
                    <td className="px-5 py-3.5">
                      <div className="flex flex-wrap gap-1.5">
                        {s.skills.map((sk) => (
                          <SkillTag key={sk} skill={sk} />
                        ))}
                      </div>
                    </td>
                    <td className="px-5 py-3.5 text-slate-600">{s.eligibleCompanies}</td>
                    <td className="px-5 py-3.5">
                      <span
                        className={`rounded-full border px-2.5 py-1 text-[11px] font-semibold ${
                          s.status === "Eligible"
                            ? "border-emerald-200 bg-emerald-50 text-emerald-700"
                            : "border-rose-200 bg-rose-50 text-rose-700"
                        }`}
                      >
                        {s.status}
                      </span>
                    </td>
                    <td className="px-5 py-3.5 text-right">
                      <ChevronRight className="h-4 w-4 text-slate-300" />
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}

      <StudentDetailPanel student={selected} onClose={() => setSelected(null)} />
    </div>
  );
}
