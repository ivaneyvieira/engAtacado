SELECT E.storeno AS storeno, E.ordno AS numero, E.prdno AS prdno, E.grade AS grade,
       qtty / 1000 AS quant, I.cm_real / 10000 AS preco, TRIM(MID(P.name, 1, 37)) AS descricao,
       GROUP_CONCAT(DISTINCT localizacao ORDER BY localizacao SEPARATOR '/') AS localizacao
FROM sqldados.eoprd          AS E
  INNER JOIN sqldados.eord      O
               USING (storeno, ordno)
  INNER JOIN sqldados.prd    AS P
               ON E.prdno = P.no
  INNER JOIN sqldados.stk    AS I
               ON I.storeno = E.storeno AND I.prdno = E.prdno AND I.grade = E.grade
  LEFT JOIN  sqldados.prdloc AS L
               ON E.storeno = L.storeno AND E.prdno = L.prdno AND L.localizacao != ''
WHERE O.storeno = :storeno
  AND O.ordno = :numero
GROUP BY storeno, ordno, prdno, grade