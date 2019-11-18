SELECT
    O.storeno AS storeno,
    CONCAT (O.nfname, '/', O.invse) AS numero,
    cast (issue_date AS DATE) AS data,
    usernoFirst AS userno,
    IFNULL(U.name, 'N/D') AS username,
    IFNULL(V.name, 'N/D') AS cliente,
    1 AS status
FROM sqldados.inv O
     LEFT JOIN sqldados.users AS U
       ON U.no = O.usernoFirst
     LEFT JOIN sqldados.vend  AS V
       ON V.no = O.vendno
WHERE
    O.storeno = :storeno AND
    O.nfname = :numero AND
    O.invse = '66'
GROUP BY storeno, numero