import './index.css';

const Footer = () => {
    return (
        <footer className="landing-footer">
            <p>Copyright {'&'}copy 2021 Sisters Art Store All Rights Reserved</p>
            <div class="landing-footer-icons">
                <a href="https://www.facebook.com/SistersArtOOD/" target="_blank" rel="noreferrer" className="landing-footer-link">
                <i class="fa fa-facebook" aria-hidden="true">SistersArtStore</i></a>
                <a href="https://www.facebook.com/groups/610448616214671/" target="_blank" rel="noreferrer" className="landing-footer-link">
                <i class="fa fa-facebook" aria-hidden="true">EssentialOil</i></a>
                <a href="https://www.doterra.com/" target="_blank" rel="noreferrer" className="landing-footer-link">
                <i class="fa fa-link" aria-hidden="true">DoTerra</i></a>
                </div> 
        </footer>

    );
};

export default Footer;