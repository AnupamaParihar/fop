/*
 * $Id: PDFNumber.java,v 1.9 2003/03/07 08:25:47 jeremias Exp $
 * ============================================================================
 *                    The Apache Software License, Version 1.1
 * ============================================================================
 * 
 * Copyright (C) 1999-2003 The Apache Software Foundation. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modifica-
 * tion, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. The end-user documentation included with the redistribution, if any, must
 *    include the following acknowledgment: "This product includes software
 *    developed by the Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself, if
 *    and wherever such third-party acknowledgments normally appear.
 * 
 * 4. The names "FOP" and "Apache Software Foundation" must not be used to
 *    endorse or promote products derived from this software without prior
 *    written permission. For written permission, please contact
 *    apache@apache.org.
 * 
 * 5. Products derived from this software may not be called "Apache", nor may
 *    "Apache" appear in their name, without prior written permission of the
 *    Apache Software Foundation.
 * 
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * APACHE SOFTWARE FOUNDATION OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLU-
 * DING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS
 * OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ============================================================================
 * 
 * This software consists of voluntary contributions made by many individuals
 * on behalf of the Apache Software Foundation and was originally created by
 * James Tauber <jtauber@jtauber.com>. For more information on the Apache
 * Software Foundation, please see <http://www.apache.org/>.
 */ 
package org.apache.fop.pdf;

/**
 * This class represents a simple number object. It also contains contains some 
 * utility methods for outputing numbers to PDF.
 */
public class PDFNumber extends PDFObject {

    private Number number;

    /**
     * Returns the number.
     * @return the number
     */
    public Number getNumber() {
        return this.number;
    }
    
    /**
     * Sets the number.
     * @param number the number
     */
    public void setNumber(Number number) {
        this.number = number;
    }

    /**
     * Output a Double value to a string suitable for PDF.
     *
     * @param doubleDown the Double value
     * @return the value as a string
     */
    public static String doubleOut(Double doubleDown) {
        return doubleOut(doubleDown.doubleValue());
    }

    /**
     * Output a double value to a string suitable for PDF.
     *
     * @param doubleDown the double value
     * @return the value as a string
     */
    public static String doubleOut(double doubleDown) {
        StringBuffer p = new StringBuffer();
        if (doubleDown < 0) {
            doubleDown = -doubleDown;
            p.append("-");
        }
        double trouble = doubleDown % 1;

        if (trouble > 0.950) {
            p.append((int)doubleDown + 1);
        } else if (trouble < 0.050) {
            p.append((int)doubleDown);
        } else {
            String doubleString = new String(doubleDown + "");
            int decimal = doubleString.indexOf(".");
            if (decimal != -1) {
                p.append(doubleString.substring(0, decimal));

                if ((doubleString.length() - decimal) > 6) {
                    p.append(doubleString.substring(decimal, decimal + 6));
                } else {
                    p.append(doubleString.substring(decimal));
                }
            } else {
                p.append(doubleString);
            }
        }
        return (p.toString());
    }

    /**
     * Output a double value to a string suitable for PDF.
     * In this method it is possible to set the maximum
     * number of decimal places to output.
     *
     * @param doubleDown the Double value
     * @param dec the number of decimal places to output
     * @return the value as a string
     */
    public static String doubleOut(double doubleDown, int dec) {
        StringBuffer p = new StringBuffer();
        if (doubleDown < 0) {
            doubleDown = -doubleDown;
            p.append("-");
        }
        double trouble = doubleDown % 1;

        if (trouble > (1.0 - (5.0 / (Math.pow(10.0, dec))))) {
            p.append((int)doubleDown + 1);
        } else if (trouble < (5.0 / (Math.pow(10.0, dec)))) {
            p.append((int)doubleDown);
        } else {
            String doubleString = new String(doubleDown + "");
            int decimal = doubleString.indexOf(".");
            if (decimal != -1) {
                p.append(doubleString.substring(0, decimal));

                if ((doubleString.length() - decimal) > dec) {
                    p.append(doubleString.substring(decimal, decimal + dec));
                } else {
                    p.append(doubleString.substring(decimal));
                }
            } else {
                p.append(doubleString);
            }
        }
        return (p.toString());
    }

    /**
     * @see org.apache.fop.pdf.PDFObject#toPDFString()
     */
    protected String toPDFString() {
        if (getNumber() == null) {
            throw new IllegalArgumentException(
                "The number of this PDFNumber must not be empty");
        }
        StringBuffer sb = new StringBuffer(64);
        sb.append(getObjectID());
        sb.append(getNumber().toString());
        sb.append("\nendobj\n");
        return sb.toString();
    }

}

