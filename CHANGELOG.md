# Changelog

## [Unreleased]

### Changed
- `ValidationIssue.message` renamed to `ValidationIssue.errorMessage` to distinguish the top-level issue summary from per-item messages on affected offences and defendants
- `AffectedOffence` and `AffectedDefendant` now include a required `message` field carrying a specific human-readable message for each affected item
- `ERROR` severity issues must always have `validationLevel` set to `OFFENCE`; only `WARNING` severity issues may be scoped to `DEFENDANT` level

### Added
- `AffectedDefendant` added to `ValidationIssue` alongside the existing `AffectedOffence`, supporting defendant-level validation issues
- `validationLevel` on `ValidationIssue` scopes each issue to either `OFFENCE` or `DEFENDANT`; only the corresponding `affectedOffences` or `affectedDefendants` list is populated
- Optional `dateOfBirth` field added to `DefendantDto`, to be populated once callers are updated to provide it
- `Prompt` schema added, and `ResultLineDto.prompts` now carries structured data fields (e.g. `endDate`, `endDateOfTagging`) captured alongside a result line
