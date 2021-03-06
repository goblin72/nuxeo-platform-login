/*
 * (C) Copyright 2014 Nuxeo SA (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     Nelson Silva <nelson.silva@inevo.pt>
 */
package org.nuxeo.ecm.platform.auth.saml.binding;

import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.binding.decoding.HTTPRedirectDeflateDecoder;
import org.opensaml.saml2.binding.encoding.HTTPRedirectDeflateEncoder;
import org.opensaml.ws.transport.InTransport;
import org.opensaml.ws.transport.OutTransport;
import org.opensaml.ws.transport.http.HTTPInTransport;
import org.opensaml.ws.transport.http.HTTPOutTransport;
import org.opensaml.ws.transport.http.HTTPTransport;

/**
 * HTTP Redirect Binding
 *
 * @since 6.0
 */
public class HTTPRedirectBinding extends SAMLBinding {

    public static final String SAML_REQUEST = "SAMLRequest";

    public static final String SAML_RESPONSE = "SAMLResponse";

    public HTTPRedirectBinding() {
        super(new HTTPRedirectDeflateDecoder(), new HTTPRedirectDeflateEncoder());
    }

    @Override
    public String getBindingURI() {
        return SAMLConstants.SAML2_REDIRECT_BINDING_URI;
    }

    @Override
    public boolean supports(InTransport transport) {
        if (transport instanceof HTTPInTransport) {
            HTTPTransport t = (HTTPTransport) transport;
            return "GET".equalsIgnoreCase(t.getHTTPMethod())
                    && (t.getParameterValue(SAML_REQUEST) != null || t.getParameterValue(SAML_RESPONSE) != null);
        } else {
            return false;
        }
    }

    @Override
    public boolean supports(OutTransport transport) {
        return transport instanceof HTTPOutTransport;
    }
}
