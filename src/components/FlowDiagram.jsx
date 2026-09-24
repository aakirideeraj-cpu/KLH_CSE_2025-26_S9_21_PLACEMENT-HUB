import { ArrowDown, ArrowRight } from "lucide-react";

export function FlowVertical({ steps }) {
  return (
    <div className="flex flex-col items-center">
      {steps.map((step, i) => (
        <div key={step.label} className="flex flex-col items-center">
          <div className="flex min-w-[220px] flex-col items-center gap-0.5 rounded-xl border border-slate-200 bg-white px-5 py-3.5 text-center shadow-card">
            <p className="text-[13.5px] font-semibold text-navy-800">{step.label}</p>
            {step.sub && <p className="text-[11.5px] text-slate-400">{step.sub}</p>}
          </div>
          {i < steps.length - 1 && (
            <ArrowDown className="my-1.5 h-4 w-4 text-slate-300" strokeWidth={2} />
          )}
        </div>
      ))}
    </div>
  );
}

export function FlowHorizontal({ steps }) {
  return (
    <div className="flex flex-wrap items-center justify-center gap-2">
      {steps.map((step, i) => (
        <div key={step.label} className="flex items-center gap-2">
          <div className="flex min-w-[140px] flex-col items-center gap-0.5 rounded-xl border border-slate-200 bg-white px-4 py-3 text-center shadow-card">
            <p className="text-[13px] font-semibold text-navy-800">{step.label}</p>
            {step.sub && <p className="text-[11px] text-slate-400">{step.sub}</p>}
          </div>
          {i < steps.length - 1 && (
            <ArrowRight className="h-4 w-4 shrink-0 text-slate-300" strokeWidth={2} />
          )}
        </div>
      ))}
    </div>
  );
}
