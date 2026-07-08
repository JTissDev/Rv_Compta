# Javadoc Style Guide (English US)

This guide defines mandatory Javadoc requirements for the project.

Class header template:
```
/**
 * Short one-line responsibility statement.
 *
 * Longer description if necessary, describing invariants or usage.
 *
 * @author J.Tiss
 * @since 0.6.0
 * @version 0.6.x
 * @email jtissdev@gmail.com
 */
```

Method header rules:
- One-line imperative summary: "Return the account balance." or "Set the journal name."
- @param for each parameter (describe meaning, units, constraints)
- @return for non-void values (describe returned semantics)
- @throws for checked exceptions and any runtime exceptions that are not obvious
- Use English (US) throughout

Field-level Javadoc only for public/protected fields.

Examples and templates are provided in reports/templates/ (in this branch).
