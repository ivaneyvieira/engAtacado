SELECT
    O.storeno AS storeno,
    CONCAT (
           O.nfno,
           '/',
           O.nfse) AS numero,
    cast (issuedate AS DATE) AS date,
    0 AS userno,
    '' AS username,
    IFNULL (
           C.name,
           'N/D') AS cliente,
    1 AS status
FROM sqldados.nf O
     LEFT JOIN sqldados.custp
               AS C ON C.no = O.custno
WHERE
    O.storeno = :storeno AND
    O.nfno = :numero AND
    O.nfse = '66'
GROUP BY
    storeno,
    numero