// A real (small-scale) Rabin-Karp implementation used ONLY to power this
// frontend prototype's Pattern Search demo. This is NOT the final
// implementation — the actual DSA project will reimplement Rabin-Karp in
// Java and run it against documents stored in MySQL via JDBC.

const BASE = 256;
const MODULUS = 101;

export function rabinKarpSearch(text, pattern) {
  const positions = [];
  const n = text.length;
  const m = pattern.length;
  if (m === 0 || m > n) return positions;

  let patternHash = 0;
  let windowHash = 0;
  let h = 1;

  for (let i = 0; i < m - 1; i++) {
    h = (h * BASE) % MODULUS;
  }

  for (let i = 0; i < m; i++) {
    patternHash = (BASE * patternHash + pattern.charCodeAt(i)) % MODULUS;
    windowHash = (BASE * windowHash + text.charCodeAt(i)) % MODULUS;
  }

  for (let i = 0; i <= n - m; i++) {
    if (patternHash === windowHash) {
      // Hash matched — verify character by character to rule out collisions.
      let match = true;
      for (let j = 0; j < m; j++) {
        if (text[i + j] !== pattern[j]) {
          match = false;
          break;
        }
      }
      if (match) positions.push(i);
    }

    if (i < n - m) {
      windowHash =
        (BASE * (windowHash - text.charCodeAt(i) * h) + text.charCodeAt(i + m)) % MODULUS;
      if (windowHash < 0) windowHash += MODULUS;
    }
  }

  return positions;
}

// Case-insensitive convenience wrapper for the UI search box.
export function searchDocuments(documents, rawPattern) {
  const pattern = rawPattern.trim();
  if (!pattern) return [];

  return documents.map((doc) => {
    const positions = rabinKarpSearch(doc.text.toLowerCase(), pattern.toLowerCase());
    return {
      file: doc.file,
      company: doc.company,
      text: doc.text,
      positions,
      found: positions.length > 0,
    };
  });
}
