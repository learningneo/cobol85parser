 IDENTIFICATION DIVISION.
 PROGRAM-ID. PERFORMPROCEDURE.
 PROCEDURE DIVISION.
 INIT.
     PERFORM PROC1.
     PERFORM PROC2.
     PERFORM PROC1.
     STOP RUN.
 PROC1.
     Display "Proc1".
 PROC2.
     Display "Proc2".