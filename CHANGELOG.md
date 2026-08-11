# Changelog

## [Unreleased]

### Added
- Optional `dateOfBirth` field added to `DefendantDto`, to be populated once callers are updated to provide it

## [Released]

### Changed
- `DraftValidationResponse.errors` now returns a `ValidationErrors` container instead of a raw array of `ValidationIssue`; the old `ValidationIssue.message` field was removed, replaced by `ValidationErrors.errorMessages` (top-level human-readable summaries) and per-item `message` fields on `AffectedOffence`/`AffectedDefendant`
- `ERROR` severity issues must always have `validationLevel` set to `OFFENCE`; only `WARNING` severity issues may be scoped to `DEFENDANT` level

### Added
- `AffectedDefendant` added to `ValidationIssue` alongside the existing `AffectedOffence`, supporting defendant-level validation issues
- `validationLevel` on `ValidationIssue` scopes each issue to either `OFFENCE` or `DEFENDANT`; only the corresponding `affectedOffences` or `affectedDefendants` list is populated
- `AffectedOffence` and `AffectedDefendant` now include a required `message` field carrying a specific human-readable message for each affected item
- `Prompt` schema added, and `ResultLineDto.prompts` now carries structured data fields (e.g. `endDate`, `endDateOfTagging`) captured alongside a result line
