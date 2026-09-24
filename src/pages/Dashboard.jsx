import { Users, Building2, UserCheck, GitMerge } from "lucide-react";
import {
  AreaChart,
  Area,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
} from "recharts";
import StatCard from "../components/StatCard";
import SkillTag from "../components/SkillTag";
import { companies } from "../data/companies";
import { students } from "../data/students";

const topCandidates = [...students]
  .sort((a, b) => b.matchScore - a.matchScore)
  .slice(0, 5);

const activity = [
  { month: "May", students: 60, companies: 8, matches: 120 },
  { month: "Jun", students: 110, companies: 16, matches: 260 },
  { month: "Jul", students: 155, companies: 24, matches: 480 },
  { month: "Aug", students: 190, companies: 31, matches: 720 },
  { month: "Sep", students: 228, companies: 37, matches: 990 },
  { month: "Oct", students: 250, companies: 42, matches: 1240 },
];

const statusStyles = {
  Open: "bg-emerald-50 text-emerald-700 border-emerald-200",
  Upcoming: "bg-amber-50 text-amber-700 border-amber-200",
};

export default function Dashboard() {
  return (
    <div className="animate-fadeUp space-y-8">
      <div>
        <h1 className="font-display text-[24px] font-bold text-navy-900 sm:text-[26px]">
          Good Morning, Placement Team 👋
        </h1>
        <p className="mt-1.5 text-[14px] text-slate-500">
          Track students, companies and placement opportunities.
        </p>
      </div>

      <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-4">
        <StatCard icon={Users} label="Total Students" value="250" delta="+12 this month" tone="navy" />
        <StatCard icon={Building2} label="Total Companies" value="42" delta="+3 this month" tone="accent" />
        <StatCard icon={UserCheck} label="Eligible Candidates" value="187" delta="+21 this month" tone="emerald" />
        <StatCard icon={GitMerge} label="Placement Matches" value="1,240" delta="+180 this month" tone="amber" />
      </div>

      <div className="grid grid-cols-1 gap-6 xl:grid-cols-5">
        {/* Recent Placement Drives */}
        <div className="rounded-2xl border border-slate-200 bg-white p-5 shadow-card xl:col-span-3">
          <div className="mb-4 flex items-center justify-between">
            <h2 className="font-display text-[16px] font-bold text-navy-900">
              Recent Placement Drives
            </h2>
            <span className="text-[12px] text-slate-400">5 active drives</span>
          </div>
          <div className="overflow-x-auto">
            <table className="w-full min-w-[520px] text-left text-[13px]">
              <thead>
                <tr className="text-[11.5px] uppercase tracking-wide text-slate-400">
                  <th className="pb-2.5 font-medium">Company</th>
                  <th className="pb-2.5 font-medium">CGPA Cutoff</th>
                  <th className="pb-2.5 font-medium">Required Skills</th>
                  <th className="pb-2.5 font-medium">Status</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-slate-100">
                {companies.map((c) => (
                  <tr key={c.id} className="transition-colors hover:bg-slate-50/80">
                    <td className="py-3 pr-2 font-semibold text-navy-800">{c.name}</td>
                    <td className="py-3 pr-2 text-slate-600">{c.cgpaCutoff.toFixed(2)}</td>
                    <td className="py-3 pr-2">
                      <div className="flex flex-wrap gap-1.5">
                        {c.skills.map((s) => (
                          <SkillTag key={s} skill={s} />
                        ))}
                      </div>
                    </td>
                    <td className="py-3">
                      <span
                        className={`rounded-full border px-2.5 py-1 text-[11px] font-semibold ${statusStyles[c.status]}`}
                      >
                        {c.status}
                      </span>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>

        {/* Top Candidates */}
        <div className="rounded-2xl border border-slate-200 bg-white p-5 shadow-card xl:col-span-2">
          <div className="mb-4 flex items-center justify-between">
            <h2 className="font-display text-[16px] font-bold text-navy-900">Top Candidates</h2>
            <span className="text-[12px] text-slate-400">By match score</span>
          </div>
          <div className="space-y-1">
            {topCandidates.map((s, i) => (
              <div
                key={s.id}
                className="flex items-center gap-3 rounded-xl px-2 py-2.5 transition-colors hover:bg-slate-50"
              >
                <span className="flex h-7 w-7 shrink-0 items-center justify-center rounded-full bg-navy-50 text-[12px] font-bold text-navy-700">
                  {i + 1}
                </span>
                <div className="min-w-0 flex-1">
                  <p className="truncate text-[13.5px] font-semibold text-navy-800">{s.name}</p>
                  <p className="text-[11.5px] text-slate-400">
                    CGPA {s.cgpa.toFixed(2)} · {s.skills.slice(0, 2).join(", ")}
                  </p>
                </div>
                <span className="shrink-0 rounded-full bg-accent-50 px-2.5 py-1 text-[11.5px] font-bold text-accent-700">
                  {s.matchScore}%
                </span>
              </div>
            ))}
          </div>
        </div>
      </div>

      {/* Placement Activity Chart */}
      <div className="rounded-2xl border border-slate-200 bg-white p-5 shadow-card sm:p-6">
        <div className="mb-1 flex items-center justify-between">
          <h2 className="font-display text-[16px] font-bold text-navy-900">Placement Activity</h2>
          <span className="text-[12px] text-slate-400">Last 6 months</span>
        </div>
        <p className="mb-4 text-[12.5px] text-slate-400">
          Students registered, companies added, and matches generated over time.
        </p>
        <div className="h-72 w-full">
          <ResponsiveContainer width="100%" height="100%">
            <AreaChart data={activity} margin={{ top: 10, right: 8, left: -18, bottom: 0 }}>
              <defs>
                <linearGradient id="matches" x1="0" y1="0" x2="0" y2="1">
                  <stop offset="0%" stopColor="#3763f0" stopOpacity={0.28} />
                  <stop offset="100%" stopColor="#3763f0" stopOpacity={0} />
                </linearGradient>
                <linearGradient id="studentsG" x1="0" y1="0" x2="0" y2="1">
                  <stop offset="0%" stopColor="#141b40" stopOpacity={0.18} />
                  <stop offset="100%" stopColor="#141b40" stopOpacity={0} />
                </linearGradient>
              </defs>
              <CartesianGrid strokeDasharray="3 3" stroke="#eef1f6" vertical={false} />
              <XAxis
                dataKey="month"
                tick={{ fontSize: 12, fill: "#94a3b8" }}
                axisLine={{ stroke: "#eef1f6" }}
                tickLine={false}
              />
              <YAxis tick={{ fontSize: 12, fill: "#94a3b8" }} axisLine={false} tickLine={false} />
              <Tooltip
                contentStyle={{
                  borderRadius: 10,
                  border: "1px solid #e2e8f0",
                  fontSize: 12.5,
                  boxShadow: "0 8px 24px -12px rgba(11,17,48,0.2)",
                }}
              />
              <Area
                type="monotone"
                dataKey="matches"
                name="Matches"
                stroke="#3763f0"
                strokeWidth={2.5}
                fill="url(#matches)"
              />
              <Area
                type="monotone"
                dataKey="students"
                name="Students"
                stroke="#141b40"
                strokeWidth={2}
                fill="url(#studentsG)"
              />
            </AreaChart>
          </ResponsiveContainer>
        </div>
        <div className="mt-2 flex items-center justify-center gap-5 text-[12px] text-slate-500">
          <LegendDot color="#3763f0" label="Matches" />
          <LegendDot color="#141b40" label="Students" />
        </div>
      </div>
    </div>
  );
}

function LegendDot({ color, label }) {
  return (
    <span className="flex items-center gap-1.5">
      <span className="h-2 w-2 rounded-full" style={{ backgroundColor: color }} />
      {label}
    </span>
  );
}
