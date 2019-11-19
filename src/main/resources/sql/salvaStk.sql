UPDATE sqldados.stk
  SET longReserva1 = qtty_atacado
WHERE longReserva2 <> :numero
  AND storeno = :storeno
  AND prdno = :prdno
  AND grade = :grade;

UPDATE sqldados.stk
  SET qtty_atacado = qtty_atacado + :qtty,
  last_date = CURRENT_DATE * 1
WHERE longReserva2 <> :numero
  AND storeno = :storeno
  AND prdno = :prdno
  AND grade = :grade;

UPDATE sqldados.stk
  SET longReserva2 = :numero
WHERE storeno = :storeno
  AND prdno = :prdno
  AND grade = :grade
