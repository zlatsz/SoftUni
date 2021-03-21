import "./index.css"
import { Component } from 'react';
import { Link } from 'react-router-dom';
import Footer from "../Landing-page/Footer"

class Login extends Component {
    state = {
        email: '',
        password: '',
        repeatPassword: ''
    };

    changeHandler(event) {
        this.setState({value: event.target.value});
      }
      submitHandler(event) {
        event.preventDefault();
        // Doing some AJAX with the data...
      }
    

    render() {
        const { email, password, repeatPassword } = this.state;
        return (
            <>
                <section className="register-page">
                    <Link to="/" className="register-logo">
                        <img src="logo.png" alt="logo" />
                    </Link>
                    <article className="register-page-wrapper">
                        <h2>Регистрирай се:</h2>
                        <form className="register-page-content" onSubmit={this.submitHandler}>
                            <i className="fas fa-envelope fa-lg" aria-hidden="true"></i>
                            <input type="text" placeholder="Email" value={email} onChange={this.changeHandler} />

                            <i className="fa fa-lock fa-lg" aria-hidden="true"></i>
                            <input type="password" placeholder="Password" value={password} onChange={this.changeHandler} />

                            <i className="fa fa-unlock fa-lg" aria-hidden="true"></i>
                            <input type="password" placeholder="Repeat Password" value={repeatPassword} onChange={this.changeHandler} />

                            <button type="submit" className="register-page-submit" value="">Влез</button>
                        </form>
                        <h3>Ако си регистриран влез от <Link to="/login"><span>ТУК</span></Link></h3>
                    </article>
                </section>
                <Footer />
            </>
        );
    };

};

export default Login;