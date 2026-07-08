---
# PcgDataMapper: applied translations and minor cleanup

The following automated changes were applied to replace French log messages and comments with English (US) equivalents where safe and non-breaking.

Changed items (examples):
- logger messages:
  - "pcg loaded \n JSON Array size: " -> "PCG loaded. JSON array size: {}"
  - exception messages: "Failed to load pcg resource : " -> "Failed to load PCG resource: {}"

Notes:
- Deprecated methods and French comments were left as-is where they are internal to the module but non-user-facing. The main user-facing logs have been converted to English.
- No API-breaking renames were applied.
---