SELECT
    O.storeno AS storeno,
    cast(CONCAT(O.nfno, '/', O.nfse) AS char) AS numero,
    cast(issuedate AS DATE) AS data,
    0 AS userno,
    '' AS username,
    IFNULL(C.name, 'N/D') AS cliente,
    1 AS status,
    'S' AS origem,
    cast(O.eordno AS CHAR) AS pedido
FROM sqldados.nf O
     LEFT JOIN sqldados.custp AS C ON C.no = O.custno
WHERE
    O.storeno = :storeno AND
    (O.nfno = :numero OR :numero = '') AND
    (O.eordno = :pedido OR :pedido = '') AND
    O.nfse = '66'
GROUP BY storeno, numero