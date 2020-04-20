import React from "react";

class Pagination extends React.Component{

    renderPageNumber(i) {
        if(i<1|| i>this.getMaxPage()) {
            return;
        }

        if(i=== this.props.currentPage) {
            return (
                <li className="page-item active">
                    <a className="page-link" onClick={() => this.props.onClick(i)}>{i}</a>
                </li>
            );
        } else {
            return (
                <li className="page-item">
                    <a className="page-link" onClick={() => this.props.onClick(i)}>{i}</a>
                </li>
            );
        }

    }

    getMaxPage() {
        return Math.ceil(this.props.totalCount/this.props.moviesPerPage);
    }


    render() {
        const i = this.props.currentPage;

        return <div >
            <ul className="pagination justify-content-center" >
                <li className={i===1 ? "page-item disabled" : "page-item"}>
                    <a className="page-link" onClick={() => {
                        if(i===1) return;
                        this.props.onClick(i-1)}
                    }>&laquo;</a>
                </li>
                {this.renderPageNumber(i-2)}
                {this.renderPageNumber(i-1)}
                {this.renderPageNumber(i)}
                {this.renderPageNumber(i+1)}
                {this.renderPageNumber(i+2)}
                <li className={i===this.getMaxPage() ? "page-item disabled" : "page-item"}>
                    <a className="page-link" onClick={() => {
                        if(i===this.getMaxPage()) return;
                        this.props.onClick(i+1)
                    }}>&raquo;</a>
                </li>
            </ul>
        </div>
    }
}

export default Pagination;