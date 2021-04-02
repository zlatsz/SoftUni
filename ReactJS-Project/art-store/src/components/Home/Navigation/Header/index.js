import "./index.css"
import { Link, NavLink, withRouter } from 'react-router-dom';
import { Component } from 'react';
import { AuthContext } from '../../../../contexts/authentication';
import firebase from "../../../../utils/firebase";

class Header extends Component {

    static contextType = AuthContext;

    constructor(props) {
        super(props);

        this.state = {
            search: '',
        };

        this.logout = this.logout.bind(this);
    }

    logout = () => {
        firebase.auth().signOut().then(() => {
            this.props.history.push('/')
        }).catch((error) => {
            console.log(error.message)
        });
    }

    render() {
        const { search } = this.state;
        const currentUser = this.context;
        let userProperty = Object.values(currentUser);
        return (
            <section className="header-navigation-wrapper">
                <section className="header-navigation-upper">
                    <h3>Sisters Art Store</h3>
                    <Link to="/home" className="header-home-logo">
                        <img src={process.env.PUBLIC_URL + '/logo.png'} alt="logo" />
                    </Link>
                    <form className="home-search" onSubmit={this.submitHandler}>
                        <i className="fas fa-phone-volume">0888808808</i>
                        <input type="text" id="search" />

                        <button type="submit" className="home-search-btn"><i className="fa fa-search fa-lg"></i></button>

                    </form>
                </section>
                <nav className="header-navigation-bottom">
                    <ul className="header-navigation-bottom-left">
                        <li className="header-navigation-bottom-left-item">
                            <a className="header-navigation-bottom-left-title"> Продукти </a>
                            <ul className="header-navigation-bottom-left-sub">
                                <li className="header-navigation-bottom-left-sub-item ">
                                    <NavLink to="/products/jewelleries">Бижута</NavLink>
                                </li>
                                <li className="header-navigation-bottom-left-sub-item ">
                                    <NavLink to="/products/cosmetics">Козметика</NavLink>
                                </li>
                                <li className="header-navigation-bottom-left-sub-item ">
                                    <NavLink to="/products/carts">Картички</NavLink>
                                </li>
                                <li className="header-navigation-bottom-left-sub-item ">
                                    <NavLink to="/products/others">Още...</NavLink>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <NavLink to="/about-us"> За нас </NavLink>
                        </li>
                        <li>
                            <NavLink to="/blog"> Блог </NavLink>
                        </li>
                    </ul>
                    { userProperty[0].isAdmin &&
                        <ul className="header-navigation-bottom-middle">
                            <li className="header-navigation-bottom-middle-products">
                                <a> Добави <i className="fa fa-caret-down" aria-hidden="true"></i></a>
                                <ul className="header-navigation-bottom-middle-sub">
                                    <li className="header-navigation-bottom-middle-sub-item ">
                                        <NavLink to="/add-products">Добави продукт</NavLink>
                                    </li>
                                    <li className="header-navigation-bottom-middle-sub-item ">
                                        <NavLink to="/add-posts">Добави статия</NavLink>
                                    </li>
                                </ul>
                            </li>
                            <li className="header-navigation-bottom-middle-profiles">
                                <NavLink to="/profiles"> Поръчки </NavLink>
                            </li>
                        </ul>
                    }
                    <ul className="header-navigation-bottom-right">
                        <li className="header-navigation-bottom-right-profile">
                            <NavLink to="/profile"> <i className="fas fa-user fa-lg" /></NavLink>
                        </li>
                        <li className="header-navigation-bottom-right-profile">
                            <NavLink to="/cart"> <i className="fas fa-shopping-cart"></i></NavLink>
                        </li>
                        <li className="header-navigation-bottom-right-profile" onClick={this.logout}>
                            <a> <i className="fas fa-sign-out-alt"></i> </a>
                        </li>
                    </ul>
                </nav>


            </section>
        );
    };
};

export default withRouter(Header);