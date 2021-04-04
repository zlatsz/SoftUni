import Header from "../Home/Navigation/Header/";
import Footer from "../Home/Footer";

const Deliveries= () => {

    return (
        <>
            <header className="home-header">
                <Header />
                <h3 className="home-header-category-title">
                    Благодарим, че пазарувате от нас!
                 </h3>
            </header>
            <section className="cart-wrapper">
               
               <h3>Бъдеща фунционалност за избор на доставчик и финализиране на поръчката</h3>
               
            </section>
            <Footer />
        </>
    );
}

export default Deliveries;