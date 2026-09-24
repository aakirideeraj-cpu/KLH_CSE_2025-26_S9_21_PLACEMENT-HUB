export default function EmptyState({ icon: Icon, title, description }) {
  return (
    <div className="flex flex-col items-center justify-center rounded-2xl border border-dashed border-slate-300 bg-slate-50/60 px-6 py-14 text-center">
      {Icon && (
        <div className="mb-3.5 flex h-11 w-11 items-center justify-center rounded-full bg-white shadow-sm">
          <Icon className="h-5 w-5 text-slate-400" strokeWidth={1.75} />
        </div>
      )}
      <p className="text-[14px] font-semibold text-navy-800">{title}</p>
      {description && <p className="mt-1 max-w-xs text-[13px] text-slate-500">{description}</p>}
    </div>
  );
}
