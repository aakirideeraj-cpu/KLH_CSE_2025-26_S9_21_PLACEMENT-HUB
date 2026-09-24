// Mock placement document corpus.
// In the final system these documents are read from MySQL / disk and searched
// by a Java implementation of Rabin-Karp. Here we run a real Rabin-Karp pass
// client-side over this static corpus purely to demonstrate the UI/UX —
// nothing here is connected to the actual Java/JDBC pattern-search module.

export const documents = [
  {
    file: "TCS.txt",
    company: "TCS",
    text: `TCS Placement Drive Notification - Assistant System Engineer
Eligibility: B.Tech CSE, IT, ECE with CGPA 7.5 and above, no active backlogs.
Required Skills: Java, SQL, and basic problem solving. Candidates with prior
Java project experience will be preferred during the technical round.
Round 1: Online Assessment covering aptitude, verbal ability and coding in Java.
Round 2: Technical Interview focused on Java fundamentals, OOP concepts, and SQL queries.
Round 3: HR Interview and offer discussion.
Selected candidates will undergo a 3 month training program before onboarding.`,
  },
  {
    file: "Infosys.txt",
    company: "Infosys",
    text: `Infosys Systems Engineer Recruitment 2026
Eligibility Criteria: CGPA of 8.0 or higher across all semesters.
Skills Required: Python programming, SQL database concepts, and strong logical reasoning.
The selection process includes an online coding test, a technical interview, and
an HR discussion. Shortlisted students will be evaluated on data structures,
Python scripting, and query optimization in SQL. Certification in Python is a plus.`,
  },
  {
    file: "Accenture.txt",
    company: "Accenture",
    text: `Accenture Associate Software Engineer - Campus Hiring
Minimum CGPA required: 7.0. Open to all branches.
Core Skills: Java and HTML for front-end and back-end fundamentals.
Selection Rounds: Cognitive Assessment, Technical Interview, and HR Round.
Candidates should be comfortable with Java syntax, basic web development using
HTML, and general problem solving. Prior internship experience is an advantage
but not mandatory for freshers applying through this drive.`,
  },
  {
    file: "Wipro.txt",
    company: "Wipro",
    text: `Wipro Project Engineer Hiring Announcement
Eligibility: CGPA 8.5 and above, CSE / IT / ECE branches only.
Required Technical Skills: Java, SQL, and Python for full-stack project assignments.
The recruitment process consists of an elite national qualifier test, a technical
interview evaluating Java and Python proficiency, SQL query writing, and a final
HR round. Selected candidates work on live client projects using Java and SQL
within their first year.`,
  },
  {
    file: "Amazon.txt",
    company: "Amazon",
    text: `Amazon SDE Internship - Placement Notification
Eligibility: CGPA 8.5+, strong foundation in Data Structures and Algorithms.
Preferred Skills: Java or Python, with working knowledge of Data Structures,
time and space complexity analysis, and system design basics.
Interview Process: Two rounds of technical interviews focused on coding,
Data Structures, and problem solving, followed by a bar-raiser round.
Candidates are expected to write clean, efficient code during live interviews.`,
  },
];
