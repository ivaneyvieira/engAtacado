INSERT IGNORE INTO sqldados.stk(qtty_varejo, qtty_atacado, cm_varejo, cm_real, last_date, last_qtty,
                                last_cost, avg_date, chk_qtty, chk_date, chk_empno, lastchangedt,
                                longReserva1, longReserva2, longReserva3, longReserva4,
                                moneyReserva1, moneyReserva2, moneyReserva3, moneyReserva4, padbyte,
                                l1, l2, l3, l4, m1, m2, m3, m4, cm_varejo_otn, cm_real_otn, storeno,
                                shortReserva1, shortReserva2, bits, s1, s2, s3, s4, prdno, grade,
                                last_doc, c1)
SELECT qtty_varejo, qtty_atacado, cm_varejo, cm_real, last_date, last_qtty, last_cost, avg_date,
       chk_qtty, chk_date, chk_empno, lastchangedt, longReserva1, 0 AS longReserva2, longReserva3,
       longReserva4, moneyReserva1, moneyReserva2, moneyReserva3, moneyReserva4, padbyte, l1, l2,
       l3, l4, m1, m2, m3, m4, cm_varejo_otn, cm_real_otn, 10 AS storeno, shortReserva1,
       shortReserva2, bits, s1, s2, s3, s4, prdno, grade, last_doc, c1
FROM sqldados.stk
WHERE storeno = 4
  AND prdno = :prdno
  AND grade = :grade;

UPDATE sqldados.stk
SET longReserva1 = qtty_atacado
WHERE longReserva2 <> :numero
  AND storeno = :storeno
  AND prdno = :prdno
  AND grade = :grade;

UPDATE sqldados.stk
SET qtty_atacado = qtty_atacado + :qtty * 1000,
    last_date    = CURRENT_DATE * 1
WHERE longReserva2 <> :numero
  AND storeno = :storeno
  AND prdno = :prdno
  AND grade = :grade;

UPDATE sqldados.stk
SET longReserva2 = :numero
WHERE storeno = :storeno
  AND prdno = :prdno
  AND grade = :grade
