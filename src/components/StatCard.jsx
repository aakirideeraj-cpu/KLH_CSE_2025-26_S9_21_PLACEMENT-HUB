export default function StatCard({ icon: Icon, label, value, delta, tone = "navy" }) {
  const tones = {
    navy: "bg-navy-50 text-navy-700",
    accent: "bg-accent-50 text-accent-600",
    emerald: "bg-emerald-50 text-emerald-600",
    amber: "bg-amber-50 text-amber-600",
  };

  return (
    <div className="group rounded-2xl border border-slate-200 bg-white p-5 shadow-card transition-shadow hover:shadow-cardHover">
      <div className="flex items-start justify-between">
        <div className={`flex h-10 w-10 items-center justify-center rounded-xl ${tones[tone]}`}>
          <Icon className="h-5 w-5" strokeWidth={2} />
        </div>
        {delta && (
          <span className="rounded-full bg-emerald-50 px-2 py-0.5 text-[11px] font-semibold text-emerald-600">
            {delta}
          </span>
        )}
      </div>
      <p className="mt-4 font-display text-[26px] font-bold leading-none text-navy-900">
        {value}
      </p>
      <p className="mt-1.5 text-[13px] text-slate-500">{label}</p>
    </div>
  );
}
