import { Loader2 } from "lucide-react";

export function Spinner({ label = "Loading..." }) {
  return (
    <div className="flex items-center justify-center gap-2.5 py-14 text-slate-400">
      <Loader2 className="h-4 w-4 animate-spin" />
      <span className="text-[13px]">{label}</span>
    </div>
  );
}

export function SkeletonRow() {
  return (
    <div className="flex items-center gap-4 rounded-xl border border-slate-200 bg-white p-4">
      <div className="h-10 w-10 shrink-0 animate-pulse rounded-full bg-slate-200" />
      <div className="flex-1 space-y-2">
        <div className="h-3 w-1/3 animate-pulse rounded bg-slate-200" />
        <div className="h-2.5 w-1/2 animate-pulse rounded bg-slate-100" />
      </div>
    </div>
  );
}
