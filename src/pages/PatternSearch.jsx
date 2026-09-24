import { useState } from "react";
import {
  Search,
  FileText,
  CheckCircle2,
  XCircle,
  Database,
  SearchCode,
} from "lucide-react";
import { FlowHorizontal } from "../components/FlowDiagram";
import { Spinner } from "../components/LoadingState";
import EmptyState from "../components/EmptyState";
import { documents } from "../data/documents";
import { searchDocuments } from "../data/rabinKarp";

function HighlightedPreview({ text, pattern, positions }) {
  if (!positions.length) {
    return <p className="text-[12.5px] leading-relaxed text-slate-400">{text.slice(0, 160)}…</p>;
  }
  const first = positions[0];
  const start = Math.max(0, first - 60);
  const end = Math.min(text.length, first + pattern.length + 60);
  const before = text.slice(start, first);
  const match = text.slice(first, first + pattern.length);
  const after = text.slice(first + pattern.length, end);

  return (
    <p className="text-[12.5px] leading-relaxed text-slate-500">
      {start > 0 && "… "}
      {before}
      <mark className="rounded bg-amber-200/70 px-0.5 font-semibold text-navy-900">{match}</mark>
      {after}
      {end < text.length && " …"}
    </p>
  );
}

export default function PatternSearch() {
  const [pattern, setPattern] = useState("");
  const [submitted, setSubmitted] = useState("");
  const [loading, setLoading] = useState(false);
  const [results, setResults] = useState(null);

  function runSearch(e) {
    e.preventDefault();
    const value = pattern.trim();
    if (!value) return;
    setLoading(true);
    setResults(null);
    // Simulate processing time for the visual "algorithm running" moment.
    setTimeout(() => {
      setResults(searchDocuments(documents, value));
      setSubmitted(value);
      setLoading(false);
    }, 550);
  }

  const foundCount = results ? results.filter((r) => r.found).length : 0;

  return (
    <div className="animate-fadeUp space-y-8">
      <div>
        <h1 className="font-display text-[24px] font-bold text-navy-900 sm:text-[26px]">
          Placement Document Search
        </h1>
        <p className="mt-1.5 text-[14px] text-slate-500">
          Search company placement documents using the Rabin-Karp pattern matching algorithm.
        </p>
      </div>

      {/* Search interface */}
      <div className="rounded-2xl border border-slate-200 bg-white p-6 shadow-card sm:p-8">
        <form onSubmit={runSearch} className="mx-auto max-w-xl">
          <div className="relative">
            <Search className="pointer-events-none absolute left-4 top-1/2 h-[18px] w-[18px] -translate-y-1/2 text-slate-400" />
            <input
              value={pattern}
              onChange={(e) => setPattern(e.target.value)}
              type="text"
              placeholder="Search placement documents..."
              className="focus-ring w-full rounded-xl border border-slate-200 bg-slate-50 py-3.5 pl-11 pr-32 text-[15px] text-navy-800 placeholder:text-slate-400 focus:bg-white"
            />
            <button
              type="submit"
              className="focus-ring absolute right-1.5 top-1.5 flex items-center gap-1.5 rounded-lg bg-accent-600 px-4 py-2 text-[13px] font-semibold text-white transition-colors hover:bg-accent-700 disabled:opacity-60"
              disabled={!pattern.trim() || loading}
            >
              Search Pattern
            </button>
          </div>
          <p className="mt-2.5 text-center text-[12px] text-slate-400">
            Try searching <button type="button" onClick={() => setPattern("Java")} className="focus-ring font-semibold text-accent-600 hover:underline">"Java"</button>,{" "}
            <button type="button" onClick={() => setPattern("SQL")} className="focus-ring font-semibold text-accent-600 hover:underline">"SQL"</button>, or{" "}
            <button type="button" onClick={() => setPattern("Python")} className="focus-ring font-semibold text-accent-600 hover:underline">"Python"</button>
          </p>
        </form>

        {/* Algorithm / corpus info */}
        <div className="mx-auto mt-8 grid max-w-xl grid-cols-1 gap-3 sm:grid-cols-2">
          <div className="flex items-center gap-3 rounded-xl border border-slate-200 bg-slate-50 px-4 py-3.5">
            <div className="flex h-9 w-9 shrink-0 items-center justify-center rounded-lg bg-navy-900 text-white">
              <SearchCode className="h-[18px] w-[18px]" />
            </div>
            <div>
              <p className="text-[11px] font-medium uppercase tracking-wide text-slate-400">
                Search Algorithm
              </p>
              <p className="text-[13.5px] font-semibold text-navy-800">Rabin-Karp</p>
            </div>
          </div>
          <div className="flex items-center gap-3 rounded-xl border border-slate-200 bg-slate-50 px-4 py-3.5">
            <div className="flex h-9 w-9 shrink-0 items-center justify-center rounded-lg bg-accent-600 text-white">
              <Database className="h-[18px] w-[18px]" />
            </div>
            <div>
              <p className="text-[11px] font-medium uppercase tracking-wide text-slate-400">
                Corpus
              </p>
              <p className="text-[13.5px] font-semibold text-navy-800">
                {documents.length} placement documents
              </p>
            </div>
          </div>
        </div>

        {/* Document chips */}
        <div className="mx-auto mt-4 flex max-w-xl flex-wrap justify-center gap-2">
          {documents.map((d) => (
            <span
              key={d.file}
              className="flex items-center gap-1.5 rounded-full border border-slate-200 bg-white px-3 py-1.5 text-[12px] font-medium text-slate-600"
            >
              <FileText className="h-3.5 w-3.5 text-slate-400" />
              {d.file}
            </span>
          ))}
        </div>
      </div>

      {/* Results */}
      {loading && (
        <div className="rounded-2xl border border-slate-200 bg-white shadow-card">
          <Spinner label="Running Rabin-Karp over the document corpus..." />
        </div>
      )}

      {!loading && results && (
        <div className="animate-fadeUp space-y-4">
          <div className="flex flex-wrap items-center justify-between gap-3 rounded-2xl border border-slate-200 bg-white p-5 shadow-card">
            <div>
              <p className="text-[11.5px] font-medium uppercase tracking-wide text-slate-400">
                Search Pattern
              </p>
              <p className="font-display text-[18px] font-bold text-navy-900">"{submitted}"</p>
            </div>
            <span className="rounded-full bg-emerald-50 px-3.5 py-1.5 text-[12.5px] font-semibold text-emerald-700">
              Found in {foundCount} of {documents.length} documents
            </span>
          </div>

          {results.map((r) => (
            <div
              key={r.file}
              className={`rounded-2xl border p-5 shadow-card ${
                r.found ? "border-slate-200 bg-white" : "border-slate-100 bg-slate-50/60"
              }`}
            >
              <div className="flex flex-wrap items-center justify-between gap-2">
                <div className="flex items-center gap-2.5">
                  <FileText className={`h-[18px] w-[18px] ${r.found ? "text-accent-500" : "text-slate-300"}`} />
                  <p className="text-[14.5px] font-semibold text-navy-800">{r.file}</p>
                  <span className="text-[12px] text-slate-400">· {r.company}</span>
                </div>
                {r.found ? (
                  <span className="flex items-center gap-1.5 rounded-full bg-emerald-50 px-2.5 py-1 text-[11.5px] font-semibold text-emerald-700">
                    <CheckCircle2 className="h-3.5 w-3.5" /> Pattern Found
                  </span>
                ) : (
                  <span className="flex items-center gap-1.5 rounded-full bg-slate-100 px-2.5 py-1 text-[11.5px] font-semibold text-slate-500">
                    <XCircle className="h-3.5 w-3.5" /> No Match Found
                  </span>
                )}
              </div>

              {r.found && (
                <>
                  <div className="mt-3 flex flex-wrap items-center gap-x-5 gap-y-1 text-[12.5px] text-slate-500">
                    <span>
                      Occurrences:{" "}
                      <span className="font-semibold text-navy-800">{r.positions.length}</span>
                    </span>
                    <span>
                      Match position{r.positions.length > 1 ? "s" : ""}:{" "}
                      <span className="font-mono font-semibold text-navy-800">
                        {r.positions.join(", ")}
                      </span>
                    </span>
                  </div>
                  <div className="mt-3 rounded-lg border border-slate-100 bg-slate-50 px-3.5 py-3">
                    <HighlightedPreview text={r.text} pattern={submitted} positions={r.positions} />
                  </div>
                </>
              )}
            </div>
          ))}
        </div>
      )}

      {!loading && !results && (
        <EmptyState
          icon={SearchCode}
          title="No search run yet"
          description='Enter a keyword like "Java" and press Search Pattern to run Rabin-Karp over the corpus.'
        />
      )}

      {/* Algorithm flow */}
      <div className="rounded-2xl border border-slate-200 bg-white p-5 shadow-card sm:p-6">
        <p className="mb-1 font-display text-[16px] font-bold text-navy-900">
          How Rabin-Karp Matches a Pattern
        </p>
        <p className="mb-5 text-[13px] text-slate-500">
          A rolling hash lets the algorithm slide across each document in O(1) per step,
          only doing a full character comparison when hashes collide.
        </p>
        <FlowHorizontal
          steps={[
            { label: "Pattern" },
            { label: "Hash Pattern" },
            { label: "Rolling Hash" },
            { label: "Compare with Text" },
            { label: "Verify Match" },
            { label: "Results" },
          ]}
        />
      </div>

      <div className="rounded-xl border border-dashed border-slate-300 bg-white px-5 py-4">
        <p className="text-[12px] leading-relaxed text-slate-400">
          Note: This search runs a lightweight Rabin-Karp implementation in the browser purely
          to demonstrate the interface. It is not connected to the final Java implementation —
          in the completed project, this same algorithm will run in Java against documents
          stored in MySQL and served through JDBC.
        </p>
      </div>
    </div>
  );
}
