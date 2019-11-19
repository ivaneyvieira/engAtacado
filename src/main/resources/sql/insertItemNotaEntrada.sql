DO @invno   := :invno;
DO @storeno := :storeno;
DO @numero  := :numero;
DO @prdno   := :prdno;
DO @grade   := :grade;
DO @cost    := :cost;
DO @qtty    := :qtty;

INSERT
INTO sqldados.iprd( invno, qtty, fob, cost, DATE, ipi, auxLong1, auxLong2, frete, seguro, despesas, freteIpi, qttyRessar
  , baseIcmsSubst, icmsSubst, icms, discount, fob4, cost4, icmsAliq, cfop, auxLong3, auxLong4, auxLong5, auxMy1, auxMy2,
  auxMy3, baseIcms, baseIpi, ipiAmt, reducaoBaseIcms, lucroTributado, l1, l2, l3, l4, l5, l6, l7, l8, m1, m2, m3, m4, m5
  , m6, m7, m8, storeno, bits, auxShort1, auxShort2, taxtype, auxShort3, auxShort4, auxShort5, seqno, bits2, bits3,
  bits4, s1, s2, s3, s4, s5, s6, s7, s8, prdno, grade, auxChar, auxChar2, cstIcms, cstIpi, c1)
SELECT @invno , @qtty * 1000, @cost * 100 AS fob , @cost * 100, CURRENT_DATE * 1 AS DATE , 0 AS ipi , 0 AS auxLong1 , 0 AS auxLong2
  , 0 AS frete , 0 AS seguro , 0 AS despesas , 0 AS freteIpi , 0 AS qttyRessar , 0 AS baseIcmsSubst , 0 AS icmsSubst , 0
  AS icms , 0 AS discount , @cost * 10000 AS fob4 , @cost * 10000 AS cost4 , 0 AS icmsAliq , 1949 AS cfop , 0 AS auxLong3 , 0
  AS auxLong4 , 0 AS auxLong5 , 0 AS auxMy1 , 0 AS auxMy2 , 0 AS auxMy3 , 0 AS baseIcms , 0 AS baseIpi , 0 AS ipiAmt , 0
  AS reducaoBaseIcms , 0 AS lucroTributado , 0 AS l1 , 0 AS l2 , 0 AS l3 , 0 AS l4 , 0 AS l5 , 0 AS l6 , 0 AS l7 , 0 AS
  l8 , 0 AS m1 , 0 AS m2 , 0 AS m3 , 0 AS m4 , 0 AS m5 , 0 AS m6 , 0 AS m7 , 0 AS m8 , @storeno , 32 AS bits , 0 AS
  auxShort1 , 0 AS auxShort2 , 0 AS taxtype , 0 AS auxShort3 , 0 AS auxShort4 , 0 AS auxShort5 , 0 AS seqno , 0 AS bits2
  , 0 AS bits3 , 0 AS bits4 , 0 AS s1 , 0 AS s2 , 0 AS s3 , 0 AS s4 , 0 AS s5 , 0 AS s6 , 0 AS s7 , 0 AS s8 , @prdno ,
  @grade , '' AS auxChar , '' AS auxChar2 , '000' AS cstIcms , '' AS cstIpi , '' AS c1
FROM DUAL
