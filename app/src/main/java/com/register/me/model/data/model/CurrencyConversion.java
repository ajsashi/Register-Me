package com.register.me.model.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrencyConversion {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("documentation")
    @Expose
    private String documentation;
    @SerializedName("terms_of_use")
    @Expose
    private String termsOfUse;
    @SerializedName("time_zone")
    @Expose
    private String timeZone;
    @SerializedName("time_last_update")
    @Expose
    private Integer timeLastUpdate;
    @SerializedName("time_next_update")
    @Expose
    private Integer timeNextUpdate;
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("conversion_rates")
    @Expose
    private ConversionRates conversionRates;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public String getTermsOfUse() {
        return termsOfUse;
    }

    public void setTermsOfUse(String termsOfUse) {
        this.termsOfUse = termsOfUse;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Integer getTimeLastUpdate() {
        return timeLastUpdate;
    }

    public void setTimeLastUpdate(Integer timeLastUpdate) {
        this.timeLastUpdate = timeLastUpdate;
    }

    public Integer getTimeNextUpdate() {
        return timeNextUpdate;
    }

    public void setTimeNextUpdate(Integer timeNextUpdate) {
        this.timeNextUpdate = timeNextUpdate;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public ConversionRates getConversionRates() {
        return conversionRates;
    }

    public void setConversionRates(ConversionRates conversionRates) {
        this.conversionRates = conversionRates;
    }
    public class ConversionRates {

        @SerializedName("INR")
        @Expose
        private Double iNR;
        @SerializedName("AED")
        @Expose
        private Double aED;
        @SerializedName("ARS")
        @Expose
        private Double aRS;
        @SerializedName("AUD")
        @Expose
        private Double aUD;
        @SerializedName("BGN")
        @Expose
        private Double bGN;
        @SerializedName("BRL")
        @Expose
        private Double bRL;
        @SerializedName("BSD")
        @Expose
        private Double bSD;
        @SerializedName("CAD")
        @Expose
        private Double cAD;
        @SerializedName("CHF")
        @Expose
        private Double cHF;
        @SerializedName("CLP")
        @Expose
        private Double cLP;
        @SerializedName("CNY")
        @Expose
        private Double cNY;
        @SerializedName("COP")
        @Expose
        private Double cOP;
        @SerializedName("CZK")
        @Expose
        private Double cZK;
        @SerializedName("DKK")
        @Expose
        private Double dKK;
        @SerializedName("DOP")
        @Expose
        private Double dOP;
        @SerializedName("EGP")
        @Expose
        private Double eGP;
        @SerializedName("EUR")
        @Expose
        private Double eUR;
        @SerializedName("FJD")
        @Expose
        private Double fJD;
        @SerializedName("GBP")
        @Expose
        private Double gBP;
        @SerializedName("GTQ")
        @Expose
        private Double gTQ;
        @SerializedName("HKD")
        @Expose
        private Double hKD;
        @SerializedName("HRK")
        @Expose
        private Double hRK;
        @SerializedName("HUF")
        @Expose
        private Double hUF;
        @SerializedName("IDR")
        @Expose
        private Double iDR;
        @SerializedName("ILS")
        @Expose
        private Double iLS;
        @SerializedName("ISK")
        @Expose
        private Double iSK;
        @SerializedName("JPY")
        @Expose
        private Double jPY;
        @SerializedName("KRW")
        @Expose
        private Double kRW;
        @SerializedName("KZT")
        @Expose
        private Double kZT;
        @SerializedName("MXN")
        @Expose
        private Double mXN;
        @SerializedName("MYR")
        @Expose
        private Double mYR;
        @SerializedName("NOK")
        @Expose
        private Double nOK;
        @SerializedName("NZD")
        @Expose
        private Double nZD;
        @SerializedName("PAB")
        @Expose
        private Double pAB;
        @SerializedName("PEN")
        @Expose
        private Double pEN;
        @SerializedName("PHP")
        @Expose
        private Double pHP;
        @SerializedName("PKR")
        @Expose
        private Double pKR;
        @SerializedName("PLN")
        @Expose
        private Double pLN;
        @SerializedName("PYG")
        @Expose
        private Double pYG;
        @SerializedName("RON")
        @Expose
        private Double rON;
        @SerializedName("RUB")
        @Expose
        private Double rUB;
        @SerializedName("SAR")
        @Expose
        private Double sAR;
        @SerializedName("SEK")
        @Expose
        private Double sEK;
        @SerializedName("SGD")
        @Expose
        private Double sGD;
        @SerializedName("THB")
        @Expose
        private Double tHB;
        @SerializedName("TRY")
        @Expose
        private Double tRY;
        @SerializedName("TWD")
        @Expose
        private Double tWD;
        @SerializedName("UAH")
        @Expose
        private Double uAH;
        @SerializedName("USD")
        @Expose
        private Double uSD;
        @SerializedName("UYU")
        @Expose
        private Double uYU;
        @SerializedName("ZAR")
        @Expose
        private Double zAR;

        public Double getINR() {
            return iNR;
        }

        public void setINR(Double iNR) {
            this.iNR = iNR;
        }

        public Double getAED() {
            return aED;
        }

        public void setAED(Double aED) {
            this.aED = aED;
        }

        public Double getARS() {
            return aRS;
        }

        public void setARS(Double aRS) {
            this.aRS = aRS;
        }

        public Double getAUD() {
            return aUD;
        }

        public void setAUD(Double aUD) {
            this.aUD = aUD;
        }

        public Double getBGN() {
            return bGN;
        }

        public void setBGN(Double bGN) {
            this.bGN = bGN;
        }

        public Double getBRL() {
            return bRL;
        }

        public void setBRL(Double bRL) {
            this.bRL = bRL;
        }

        public Double getBSD() {
            return bSD;
        }

        public void setBSD(Double bSD) {
            this.bSD = bSD;
        }

        public Double getCAD() {
            return cAD;
        }

        public void setCAD(Double cAD) {
            this.cAD = cAD;
        }

        public Double getCHF() {
            return cHF;
        }

        public void setCHF(Double cHF) {
            this.cHF = cHF;
        }

        public Double getCLP() {
            return cLP;
        }

        public void setCLP(Double cLP) {
            this.cLP = cLP;
        }

        public Double getCNY() {
            return cNY;
        }

        public void setCNY(Double cNY) {
            this.cNY = cNY;
        }

        public Double getCOP() {
            return cOP;
        }

        public void setCOP(Double cOP) {
            this.cOP = cOP;
        }

        public Double getCZK() {
            return cZK;
        }

        public void setCZK(Double cZK) {
            this.cZK = cZK;
        }

        public Double getDKK() {
            return dKK;
        }

        public void setDKK(Double dKK) {
            this.dKK = dKK;
        }

        public Double getDOP() {
            return dOP;
        }

        public void setDOP(Double dOP) {
            this.dOP = dOP;
        }

        public Double getEGP() {
            return eGP;
        }

        public void setEGP(Double eGP) {
            this.eGP = eGP;
        }

        public Double getEUR() {
            return eUR;
        }

        public void setEUR(Double eUR) {
            this.eUR = eUR;
        }

        public Double getFJD() {
            return fJD;
        }

        public void setFJD(Double fJD) {
            this.fJD = fJD;
        }

        public Double getGBP() {
            return gBP;
        }

        public void setGBP(Double gBP) {
            this.gBP = gBP;
        }

        public Double getGTQ() {
            return gTQ;
        }

        public void setGTQ(Double gTQ) {
            this.gTQ = gTQ;
        }

        public Double getHKD() {
            return hKD;
        }

        public void setHKD(Double hKD) {
            this.hKD = hKD;
        }

        public Double getHRK() {
            return hRK;
        }

        public void setHRK(Double hRK) {
            this.hRK = hRK;
        }

        public Double getHUF() {
            return hUF;
        }

        public void setHUF(Double hUF) {
            this.hUF = hUF;
        }

        public Double getIDR() {
            return iDR;
        }

        public void setIDR(Double iDR) {
            this.iDR = iDR;
        }

        public Double getILS() {
            return iLS;
        }

        public void setILS(Double iLS) {
            this.iLS = iLS;
        }

        public Double getISK() {
            return iSK;
        }

        public void setISK(Double iSK) {
            this.iSK = iSK;
        }

        public Double getJPY() {
            return jPY;
        }

        public void setJPY(Double jPY) {
            this.jPY = jPY;
        }

        public Double getKRW() {
            return kRW;
        }

        public void setKRW(Double kRW) {
            this.kRW = kRW;
        }

        public Double getKZT() {
            return kZT;
        }

        public void setKZT(Double kZT) {
            this.kZT = kZT;
        }

        public Double getMXN() {
            return mXN;
        }

        public void setMXN(Double mXN) {
            this.mXN = mXN;
        }

        public Double getMYR() {
            return mYR;
        }

        public void setMYR(Double mYR) {
            this.mYR = mYR;
        }

        public Double getNOK() {
            return nOK;
        }

        public void setNOK(Double nOK) {
            this.nOK = nOK;
        }

        public Double getNZD() {
            return nZD;
        }

        public void setNZD(Double nZD) {
            this.nZD = nZD;
        }

        public Double getPAB() {
            return pAB;
        }

        public void setPAB(Double pAB) {
            this.pAB = pAB;
        }

        public Double getPEN() {
            return pEN;
        }

        public void setPEN(Double pEN) {
            this.pEN = pEN;
        }

        public Double getPHP() {
            return pHP;
        }

        public void setPHP(Double pHP) {
            this.pHP = pHP;
        }

        public Double getPKR() {
            return pKR;
        }

        public void setPKR(Double pKR) {
            this.pKR = pKR;
        }

        public Double getPLN() {
            return pLN;
        }

        public void setPLN(Double pLN) {
            this.pLN = pLN;
        }

        public Double getPYG() {
            return pYG;
        }

        public void setPYG(Double pYG) {
            this.pYG = pYG;
        }

        public Double getRON() {
            return rON;
        }

        public void setRON(Double rON) {
            this.rON = rON;
        }

        public Double getRUB() {
            return rUB;
        }

        public void setRUB(Double rUB) {
            this.rUB = rUB;
        }

        public Double getSAR() {
            return sAR;
        }

        public void setSAR(Double sAR) {
            this.sAR = sAR;
        }

        public Double getSEK() {
            return sEK;
        }

        public void setSEK(Double sEK) {
            this.sEK = sEK;
        }

        public Double getSGD() {
            return sGD;
        }

        public void setSGD(Double sGD) {
            this.sGD = sGD;
        }

        public Double getTHB() {
            return tHB;
        }

        public void setTHB(Double tHB) {
            this.tHB = tHB;
        }

        public Double getTRY() {
            return tRY;
        }

        public void setTRY(Double tRY) {
            this.tRY = tRY;
        }

        public Double getTWD() {
            return tWD;
        }

        public void setTWD(Double tWD) {
            this.tWD = tWD;
        }

        public Double getUAH() {
            return uAH;
        }

        public void setUAH(Double uAH) {
            this.uAH = uAH;
        }

        public Double getUSD() {
            return uSD;
        }

        public void setUSD(Double uSD) {
            this.uSD = uSD;
        }

        public Double getUYU() {
            return uYU;
        }

        public void setUYU(Double uYU) {
            this.uYU = uYU;
        }

        public Double getZAR() {
            return zAR;
        }

        public void setZAR(Double zAR) {
            this.zAR = zAR;
        }

    }
}