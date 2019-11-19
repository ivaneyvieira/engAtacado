SELECT
    E.storeno AS storeno,
    CONCAT (O.nfno, '/', O.nfse) AS numero,
    E.prdno AS prdno,
    E.grade AS grade,
    qtty/1000 AS quant,
    IF(I.last_cost = 0 , I . cm_varejo_otn , I . last_cost ) / 10000 AS preco,
    TRIM(MID(P.name, 1, 37)) AS descricao,
    GROUP_CONCAT(DISTINCT localizacao ORDER BY localizacao SEPARATOR '/') AS localizacao
FROM sqldados.xaprd2 AS E
  INNER JOIN sqldados.nf O
    USING(storeno, pdvno, xano)
  INNER JOIN sqldados.prd AS P
    ON  E.prdno = P.no
  INNER JOIN sqldados.stk AS I
    ON  I.storeno = E.storeno
    AND I.prdno = E.prdno
    AND I.grade = E.grade
  LEFT JOIN sqldados.prdloc AS L
    ON  E.storeno = L.storeno
    AND E.prdno = L.prdno
    AND L.localizacao != ''
WHERE O.storeno = :storeno
  AND O.nfno = :numero
  AND O.nfse = '66'
GROUP BY storeno, numero, prdno, grade