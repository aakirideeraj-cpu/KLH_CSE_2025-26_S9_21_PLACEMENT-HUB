export default function SkillTag({ skill, matched, size = "sm" }) {
  const sizes = size === "sm" ? "px-2.5 py-1 text-[11.5px]" : "px-3 py-1.5 text-[13px]";
  return (
    <span
      className={`inline-flex items-center rounded-full border font-medium ${sizes} ${
        matched
          ? "border-emerald-200 bg-emerald-50 text-emerald-700"
          : "border-slate-200 bg-slate-50 text-slate-600"
      }`}
    >
      {skill}
    </span>
  );
}
