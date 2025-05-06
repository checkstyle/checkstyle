```mermaid
classDiagram
    direction TB

    class Filter {
        + accept(in event: AuditEvent): boolean
    }

    class FilterSet {
        - filters: Set
        + addFilter(in filter: Filter): void
        + removeFilter(in filter: Filter): void
        + accept(in event: AuditEvent): boolean
        + clear(): void
    }

    <<interface>> Filter
    Filter <|.. FilterSet

    class Filter:::Sky
    class FilterSet:::Sky

classDef Sky fill:#E2EBFF,stroke:#374D7C,stroke-width:1px,color:#374D7C
```
