DO @storeno := :storeno;
DO @XANO := :xano;
DO @NFNO := :numero;
DO @prdno := :prdno;
DO @grade := :grade;
DO @COST := :cost;
DO @qtty := :qtty;

INSERT INTO sqldados.xaprd(xano, nfno, price, DATE, qtty, storeno, pdvno, prdno, grade, nfse,
                           padbyte, wshash)
SELECT @XANO AS xano, @NFNO AS nfno, @COST * 100 AS price, CURRENT_DATE * 1 AS DATE, @qtty AS qtty,
       @storeno, 0 AS pdvno, @prdno, @grade, '66' AS nfse, '' AS padbyte, '' AS wshash
FROM DUAL