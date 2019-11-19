SELECT
    O.storeno AS storeno,
    CONCAT (O.nfname, '/', O.invse) AS numero,
    cast(issue_date AS DATE) AS data,
    usernoFirst AS userno,
    IFNULL(U.name, 'N/D') AS username,
    IFNULL(V.name, 'N/D') AS cliente,
    1 AS status,
    'E' as origem,
    cast(O.ordno AS CHAR) AS pedido
FROM sqldados.inv O
     LEFT JOIN sqldados.users AS U
       ON U.no = O.usernoFirst
     LEFT JOIN sqldados.vend  AS V
       ON V.no = O.vendno
WHERE
    O.storeno = :storeno AND
    (O.nfname = :numero OR :numero = '') AND
    (O.ordno = :pedido OR :pedido = '') AND
    O.invse = '66'
GROUP BY storeno, numero