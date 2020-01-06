SELECT O.storeno AS storeno, cast(CONCAT(O.storeno, ' ', O.nfname, '/', O.invse) AS CHAR) AS numero,
       cast(issue_date AS DATE) AS data, usernoFirst AS userno, IFNULL(U.name, 'N/D') AS username,
       IFNULL(V.name, 'N/D') AS cliente, 90 AS status, 'E' AS origem,
       cast(O.ordno AS CHAR) AS pedido, cast(O.nfname AS CHAR) AS nfno,
       (O.bits & POW(2, 4)) <> 0 AS cancelada
FROM sqldados.inv             O
  LEFT JOIN sqldados.users AS U
              ON U.no = O.usernoFirst
  LEFT JOIN sqldados.vend  AS V
              ON V.no = O.vendno
WHERE O.storeno = :storeno AND
      (O.nfname = :numero OR :numero = '') AND
      (O.ordno = :pedido OR :pedido = '') AND
      O.invse = '66'
GROUP BY storeno, numero