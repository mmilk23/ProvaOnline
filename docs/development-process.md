# Development Process

## Branches

- `main`: stable branch. Keep deployable and update only through pull requests.
- `development`: integration branch for day-to-day work before promotion to `main`.
- `feature/*`, `fix/*`, `chore/*`: short-lived branches created from `development`.

## Minimal Flow

1. Create a short-lived branch from `development`.
2. Open a pull request back to `development`.
3. Wait for CI and security checks to pass.
4. Merge into `development`.
5. Promote `development` to `main` through a pull request when a release is ready.

## Required Repository Rules

Configure branch protection in GitHub for `main` and `development`:

- Require pull requests before merging.
- Require the `CI` workflow to pass.
- Require the `Security` workflow for `main`.
- Require branches to be up to date before merging.
- Disable force pushes and deletions.

## Local Commands

```bash
cd provaonline-frontend
npm ci
npm run check
npm run build
```

```bash
cd provaonline-backend
mvn verify
```