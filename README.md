# producer_consumer_problem
producer-consumer-problem
Imagine you have a call center with three levels of employees: fresher, technical lead (TL), product manager (PM).
 There can be multiple employees, but only one TL or PM. 
 An incoming telephone call must be allocated to a fresher who is free.
 If no freshers are free, or if the current fresher is unable to solve the caller's problem (determined by a simple dice roll),
 he or she must escalate the call to technical lead. 
 If the TL is not free or not able to handle it,
 then the call should be escalated to the PM.
 
  1.呼叫中心有三種等級的員工：fresher, technical lead(TL), product manager(PM)
  2.這裡有很多員工，但是只有一個TL或PM 
  3.傳入一通電話必需分配給 有空的 fresher
  4.如果fresher 沒空，或者fresher沒辦法解決電話中問題(由簡單的擲骰子決定)
  5.呈4、如果沒空或fresher沒辦法解，就呼叫TL
  6.呈5、如果 TL沒空 或者也沒辦法處理問題，則 TL再轉接給PM

REQUIREMENT

  1.Create an object-oriented design for this problem. The flexible design which can be extended is preferred.
  2.Do this in an object-oriented programming language that you're comfortable with
  3.Do this with multi-threading
  4.Compilable runnable and testable code
  5.Document your code
