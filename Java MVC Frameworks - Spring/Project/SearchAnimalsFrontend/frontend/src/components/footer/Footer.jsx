import React from 'react';
import {Panel} from 'react-bootstrap';

import './footer.css';

class Footer extends React.Component {
    render() {
        return (
            <footer>
                <Panel>
                    <Panel.Footer>
                        <div className="footer-copyright text-center py-3">© 2018 Copyright:
                            <a href="https://github.com/vonrepiks" target="_blank"
                               rel="noopener noreferrer">Hristo Skipernov</a>
                        </div>
                    </Panel.Footer>
                </Panel>
            </footer>
        );
    }
}

export default Footer;