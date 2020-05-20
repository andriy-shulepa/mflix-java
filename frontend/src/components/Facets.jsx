import React from "react";
import '../bootstrap.min.css'

const INITIAL_ELEMENTS_COUNT = 5;

class Facets extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            isLoaded: false,
            facetInfo: null,
            error: null,
            itemsToShow: INITIAL_ELEMENTS_COUNT,
            expanded: false,
            selectedItems: [],
        };

        this.showMore = this.showMore.bind(this);
        this.handleCheckboxClick = this.handleCheckboxClick.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    showMore(event) {
        event.preventDefault();
        this.state.itemsToShow === INITIAL_ELEMENTS_COUNT ? (this.setState({
            itemsToShow: this.state.facetInfo.countries.length / 2,
            expanded: false
        })) : this.state.itemsToShow === this.state.facetInfo.countries.length ? (
            this.setState({itemsToShow: 5, expanded: false})
        ) : (this.setState({itemsToShow: this.state.facetInfo.countries.length, expanded: true}));
    }


    componentDidMount() {
        fetch("http://localhost:8080/api/movies/facet-info")
            .then(res => res.json())
            .then(
                (result) => {
                    console.log("Facet result: " + JSON.stringify(result));
                    this.setState({
                        isLoaded: true,
                        facetInfo: result
                    });
                },
                (error) => {
                    this.setState({
                        isLoaded: true,
                        error: error,
                    });
                }
            )
    }

    render() {
        if (!this.state.isLoaded) {
            return <div className="text-info">Loading...</div>
        }
        return (

            <div>
                <form>
                    <fieldset>
                        <legend>Countries</legend>

                        {this.state.facetInfo.countries.slice(0, this.state.itemsToShow).map(item => this.buildCheckboxItem(item))}
                        <button className="btn btn-primary" onClick={this.handleSubmit}>Apply filters</button>
                    </fieldset>
                    {/*<p>*/}

                    {/*</p>*/}
                    <p>
                        <button className="btn btn-outline-secondary" onClick={this.showMore}>
                            {this.state.expanded ? (
                                <span>Show less</span>
                            ) : (
                                <span>Show {this.calculateRemainingItems()} more</span>
                            )
                            }
                        </button>
                    </p>
                </form>
            </div>
        )
    }

    handleCheckboxClick(event) {
        let value = event.target.value;
        console.log("handleCheckboxClick "+ value);
        let newItems = this.state.selectedItems;
        if(this.state.selectedItems.includes(value)) {
            let index = this.state.selectedItems.indexOf(value);
            newItems.splice(index, 1);
        } else {
            newItems.push(value);
        }
        this.setState({selectedItems: newItems});
    }

    handleSubmit(event) {
        event.preventDefault();
        console.log("Applying filters: countries = "+ this.state.selectedItems);
    }

    calculateRemainingItems() {
        return this.state.itemsToShow === INITIAL_ELEMENTS_COUNT ?
            this.state.facetInfo.countries.length / 2 - INITIAL_ELEMENTS_COUNT
            : this.state.facetInfo.countries.length - this.state.itemsToShow;
    }

    buildCheckboxItem(item) {
        return (
            <div className="form-check" key={item._id}>
                <label className="form-check-label">
                    <input className="form-check-input" type="checkbox" value={item._id} onChange={this.handleCheckboxClick} />
                    {item._id}
                </label>
            </div>
        )
    }
}


export default Facets;