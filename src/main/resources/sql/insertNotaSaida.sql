DO @NFNO := :numero;
DO @storeno := :storeno;
DO @VALOR := :valor;
DO @ordno := :ordno;
DO @XANO := :xano;
DO @custno := :custno;

INSERT INTO sqldados.nf(xano, nfno, custno, issuedate, delivdate, sec_amt, fre_amt, netamt,
                        grossamt, discount, icms_amt,
                        tax_paid, ipi_amt, base_calculo_ipi, iss_amt, base_iss_amt, isento_amt,
                        subst_amt, baseIcmsSubst, icmsSubst, vol_no,
                        vol_qtty, cfo, invno, cfo2, auxLong1, auxLong2, auxLong3, auxLong4, auxMy1,
                        auxMy2, auxMy3, auxMy4, eordno, l1, l2, l3, l4, l5, l6, l7, l8, m1, m2, m3,
                        m4, m5, m6, m7, m8, vol_gross, vol_net, mult, storeno, pdvno, carrno, empno,
                        status,
                        natopno, xatype, storeno_from, tipo, padbits, bits, usernoCancel,
                        custno_addno, empnoDiscount, auxShort1, auxShort2,
                        auxShort3, auxShort4, auxShort5, paymno, s1, s2, s3, s4, s5, s6, s7, s8,
                        nfse, ship_by, vol_make, vol_kind, remarks,
                        padbyte, print_remarks, remarksCancel, c1, c2, wshash)
SELECT @XANO AS xano, @NFNO AS nfno, :custno AS custno, CURRENT_DATE * 1 AS issuedate,
       CURRENT_DATE * 1 AS delivdate, 0 AS sec_amt, 0 AS fre_amt, 0 AS netamt,
       @VALOR * 100 AS grossamt, 0 AS discount, 0 AS icms_amt, 0 AS tax_paid, 0 AS ipi_amt,
       0 AS base_calculo_ipi, 0 AS iss_amt, 0 AS base_iss_amt, 0 AS isento_amt, 0 AS subst_amt,
       0 AS baseIcmsSubst, 0 AS icmsSubst, 0 AS vol_no, 0 AS vol_qtty, 5949 AS cfo, 0 AS invno,
       0 AS cfo2, 0 AS auxLong1, 0 AS auxLong2, 0 AS auxLong3, 0 AS auxLong4, 0 AS auxMy1,
       0 AS auxMy2, 0 AS auxMy3, 0 AS auxMy4, @ordno AS eordno, 0 AS l1, 0 AS l2, 0 AS l3, 0 AS l4,
       0 AS l5, 0 AS l6, 0 AS l7, 0 AS l8, 0 AS m1, 0 AS m2, 0 AS m3, 0 AS m4, 0 AS m5, 0 AS m6,
       0 AS m7, 0 AS m8, 0 AS vol_gross, 0 AS vol_net, 1 AS mult, @storeno, 0 AS pdvno, 0 AS carrno,
       900 + @storeno AS empno, 0 AS status, 14 AS natopno, 0 AS xatype, 0 AS storeno_from,
       1 AS tipo /*Tranferencia*/, 0 AS padbits, 0 AS bits, 0 AS usernoCancel, 0 AS custno_addno,
       0 AS empnoDiscount, 0 AS auxShort1, 0 AS auxShort2, 0 AS auxShort3, 0 AS auxShort4,
       0 AS auxShort5, 0 AS paymno, 0 AS s1, 0 AS s2, 0 AS s3, 0 AS s4, 0 AS s5, 0 AS s6, 0 AS s7,
       0 AS s8, '66' AS nfse, '' AS ship_by, '' AS vol_make, '' AS vol_kind, '66' AS remarks,
       '' AS padbyte, '' AS print_remarks, '' AS remarksCancel, '' AS c1, '' AS c2, '' AS wshash
FROM DUAL
