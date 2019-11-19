DO @storeno := :storeno;
DO @ordno   := :ordno;
DO @valor   := :valor;
DO @INVNO   := :invno;
DO @NFNO    := :numero;
DO @vendno  := :vendno;

INSERT INTO sqldados.inv( invno, vendno, ordno, xfrno, issue_date, DATE, comp_date, ipi, icm, freight, netamt, grossamt,
  subst_trib, discount, prdamt, despesas, base_ipi, aliq, cfo, nfNfno, auxLong1, auxLong2, auxMoney1, auxMoney2,
  dataSaida, amtServicos, amtIRRF, amtINSS, amtISS, auxMoney3, auxMoney4, auxMoney5, auxLong3, auxLong4, auxLong5,
  auxLong6, auxLong7, auxLong8, auxLong9, auxLong10, auxLong11, auxLong12, auxMoney6, auxMoney7, auxMoney8, auxMoney9,
  auxMoney10, auxMoney11, auxMoney12, auxMoney13, l1, l2, l3, l4, l5, l6, l7, l8, m1, m2, m3, m4, m5, m6, m7, m8, weight
  , carrno, packages, storeno, indxno, book_bits, TYPE, usernoFirst, usernoLast, nfStoreno, bits, padbyte, auxShort1,
  auxShort2, auxShort3, auxShort4, auxShort5, auxShort6, auxShort7, auxShort8, auxShort9, auxShort10, auxShort11,
  auxShort12, auxShort13, auxShort14, bits2, bits3, bits4, bits5, s1, s2, s3, s4, s5, s6, s7, s8, nfname, invse, account
  , remarks, contaCredito, contaDebito, nfNfse, auxStr1, auxStr2, auxStr3, auxStr4, auxStr5, auxStr6, c1, c2)
SELECT @INVNO AS invno , :vendno as vendno, @ordno as ordno , 0 AS xfrno , CURRENT_DATE * 1 AS issue_date , CURRENT_DATE * 1 AS DATE ,
  CURRENT_DATE * 1 AS comp_date , 0 AS ipi , 0 AS icm , 0 AS freight , 0 AS netamt , @valor*100  AS
  grossamt , 0 AS subst_trib , 0 AS discount , @valor*100 AS prdamt , 0 AS despesas , 0 AS base_ipi , 0
  AS aliq , 1949 AS cfo , 0 AS nfNfno , 0 /*Valor desconhecido*/ AS auxLong1 , 0 AS auxLong2 , 0 AS auxMoney1 , 0 AS
  auxMoney2 , 0 AS dataSaida , 0 AS amtServicos , 0 AS amtIRRF , 0 AS amtINSS , 0 AS amtISS , 0 AS auxMoney3 , 0 AS
  auxMoney4 , 0 AS auxMoney5 , 0 AS auxLong3 , 0 AS auxLong4 , 0 AS auxLong5 , 0 AS auxLong6 , 0 AS auxLong7 ,
  CURRENT_DATE * 1 AS auxLong8 , 0 AS auxLong9 , 0 AS auxLong10 , 0 AS auxLong11 , 0 AS auxLong12 , 0 AS auxMoney6 , 0
  AS auxMoney7 , 0 AS auxMoney8 , 0 AS auxMoney9 , 0 AS auxMoney10 , 0 AS auxMoney11 , 0 AS auxMoney12 , 0 AS auxMoney13
  , 0 AS l1 , 0 AS l2 , 0 AS l3 , 0 AS l4 , 0 AS l5 , CURRENT_DATE * 1 AS l6 , 0 AS l7 , 0 AS l8 , 0 AS m1 , 0 AS m2 , 0
  AS m3 , 0 AS m4 , 0 AS m5 , 0 AS m6 , 0 AS m7 , 0 AS m8 , 0 AS weight , 0 AS carrno , 0 AS packages , @storeno , 0 AS
  indxno , 9 AS book_bits , 8 AS TYPE , 1 AS usernoFirst , 1 AS usernoLast , 0 AS nfStoreno , 3 AS bits , 0 AS padbyte ,
  @storeno AS auxShort1 , 0 AS auxShort2 , 0 AS auxShort3 , 0 AS auxShort4 , 0 AS auxShort5 , 0 AS auxShort6 , 0 AS
  /*valor desconhecido*/ auxShort7 , 0 AS /*valor desconhecido*/ auxShort8 , 0 AS auxShort9 , 0 AS auxShort10 , 0 AS
  auxShort11 , 0 AS auxShort12 , 0 AS auxShort13 , 0 AS auxShort14 , 0 AS bits2 , 0 AS bits3 , 0 AS bits4 , 0 AS bits5 ,
  0 AS s1 , 0 AS s2 , 0 AS s3 , 0 AS s4 , 0 AS s5 , 0 AS s6 , 0 AS s7 , 0 AS s8 , @NFNO AS nfname , '66' AS invse , 2
  AS account , '66' AS remarks , '' AS contaCredito , '' AS contaDebito , '' AS nfNfse , '' AS auxStr1 , '' AS auxStr2 ,
  '' AS auxStr3 , '' AS auxStr4 , '' AS auxStr5 , '' AS auxStr6 , '' AS c1 , '' AS c2
FROM DUAL