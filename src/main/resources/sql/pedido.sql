SELECT
    E.storeno AS storeno,
    cast(E.ordno AS CHAR) AS numero,
    cast(date AS DATE) AS data,
    userno AS userno,
    IFNULL(U.name, 'N/D') AS username,
    IFNULL(C.name, 'N/D') AS cliente,
    E.status AS status,
    'P' AS origem,
    cast(E.ordno AS CHAR) AS pedido
FROM
    sqldados.eord AS E
    LEFT JOIN sqldados.users AS U ON U.no = E.userno
    LEFT JOIN sqldados.custp AS C ON C.no = E.custno
WHERE E.storeno = :storeno
    AND E.ordno = :numero
GROUP BY storeno, ordno