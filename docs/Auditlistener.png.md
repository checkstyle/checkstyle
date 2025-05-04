<!-- markdownlint-disable MD046 -->

```mermaid
%%{init: {"theme":"default", "themeVariables": {
  "primaryColor": "#ffffff",
  "primaryBorderColor": "#000000",
  "primaryTextColor": "#000000",
  "shadow1": "2px 2px 4px #888888"
}}}%%

classDiagram
    class EventListener
    class EventObject

    class AuditListener {
        <<interface>>
        +auditStarted(aEvt: AuditEvent): void
        +auditFinished(aEvt: AuditEvent): void
        +fileStarted(aEvt: AuditEvent): void
        +fileFinished(aEvt: AuditEvent): void
        +addError(aEvt: AuditEvent): void
        +addException(aEvt: AuditEvent, aThrowable: Throwable): void
    }

    class AuditEvent {
        +getFileName(): String
        +getLine(): int
        +getMessage(): String
        +getColumn(): int
        +getSeverityLevel(): SeverityLevel
        +getSourceName(): String
        +getLocalizedMessage(): LocalizedMessage
    }

    AuditListener ..|> EventListener
    AuditListener ..> AuditEvent
    AuditEvent --|> EventObject
