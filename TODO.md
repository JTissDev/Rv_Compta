# TODO (English draft)

This TODO file lists prioritized, actionable tasks for the anglicization and quality baseline.

Priority: Blocker
- Convert all Javadoc to English (US) across modules and add Javadoc templates (automated). Estimated: 2d
- Convert all log messages to English (US) while preserving French user prompts. Estimated: 1d
- Add Checkstyle + PMD + Javadoc checks to CI to prevent regressions. Estimated: 1d

Priority: High
- Generate JaCoCo coverage reports and add tests for uncovered public/business-critical methods. Estimated: 3d
- Rename identifiers with French names or non-conventional casing where non-breaking. Use deprecation + adapters for breaking renames. Estimated: 3d

Priority: Medium
- Standardize CHANGELOG format across modules. Estimated: 0.5d
- Add CONTRIBUTING.md with commit conventions and review workflow. Estimated: 0.5d

Priority: Low
- Internationalization (i18n) support if you want to switch CLI prompts to other languages later.

Notes
- This branch (docs/anglicize-and-scans) contains drafts and reports. I will open focused PRs that apply non-controversial fixes directly (logs, docs, javadoc templates). For renames that might break APIs, I will propose PRs with deprecation wrappers.