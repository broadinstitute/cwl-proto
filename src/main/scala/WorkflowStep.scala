package broad.cwl.model

import shapeless.{:+:, CNil}

case class WorkflowStep(
  id: Option[String], //not actually optional but can be declared as a key for this whole object for convenience
  in: Array[WorkflowStepInput] :+: Map[WorkflowStepInputId, WorkflowStepInputSource] :+: Map[WorkflowStepInputId, WorkflowStepInput] :+: CNil,
  out: Array[String] :+: Array[WorkflowStepOutput] :+: CNil,
  run: String :+: CommandLineTool :+: ExpressionTool :+: Workflow :+: CNil,
  requirements: Option[Array[Requirement]],
  hints: Option[Array[String]], //TODO: should be 'Any' type
  label: Option[String],
  doc: Option[String],
  scatter: Option[String :+: Array[String] :+: CNil],
  scatterMethod: Option[ScatterMethod]
  
  )

case class WorkflowStepOutput(some: String)
