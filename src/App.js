import './App.css';
import React from 'react';


class MenuList extends React.Component
{
  render()
  {
    return (
      <div className="menu-list">
        <li>
          <button className="my-button" onClick={this.props.onNewGameClick}>New Game</button>
          <button className="my-button">About</button>
        </li>
      </div>
    );
  }
}

class AlphabetTable extends React.Component
{
  render()
  {
    const alphabet = [];

    for (let i = 65; i < 90; i += 3)
    {
      const subArray = [];
      
      subArray.push(String.fromCharCode(i));
      subArray.push(String.fromCharCode(i + 1));
      subArray.push(String.fromCharCode(i + 2));
    
      alphabet[i] = (subArray);
    }
    alphabet[alphabet.length - 1][2] = "";

    
    return (
      <table className="alphabet-table">
      {alphabet.map((row, index) => (
        <tr key={index}>
          <td><button className="letter-button" onClick={ ()=>{this.props.onLetterClick(row[0])} }>{row[0]}</button></td>
          <td><button className="letter-button" onClick={ ()=>{this.props.onLetterClick(row[1])} }>{row[1]}</button></td>
          <td><button className="letter-button" onClick={ ()=>{this.props.onLetterClick(row[2])} }>{row[2]}</button></td>
        </tr>
      ))}
      </table>
    );
  }
}

class LetterCell extends React.Component
{
  constructor(props)
  {
    super(props);
  }

  render()
  {
    return (
      <div className="letter-cell">
        <label className="letter">{this.props.isHidden ? "" : this.props.letter}</label>
        <div className="letter-underscore"></div>             
      </div>
    );
  }
}

class HiddenWord extends React.Component
{
  constructor(props)
  {
    super(props);

    this.word = this.props.word;

    const letterArray = [];
    for (let i = 0; i < this.word.length; ++i)
    {
      letterArray.push({ letter: this.word[i], isHidden: false });
    }

    this.state = { letterArray };
  }

  updateWord = (letter) =>
  {
    const letterArray = this.state.letterArray;
    
    while (true)
    {
      const element = letterArray.find((element) => element.isHidden && element.letter == letter);
      if (!element)
      {
        break;
      }
      element.isHidden = false;
    }

    this.setState({ letterArray });
  }

  render()
  {
    return (
      <div className="hidden-word">
      { this.state.letterArray.map((element) => 
      {
        return <LetterCell className="letter-cell" letter={element.letter} isHidden={element.isHidden} />;
      }) }
      </div>
    );
  }
}

class Game extends React.Component
{
  constructor()
  {
    super();

    this.hiddenWord = React.createRef();

    this.state = { word: "LOLITA" };

    this.imageFolder = "/images/";
    this.imageName = 7;
    this.imageExt = ".png";
  }

  componentWillMount()
  {
    fetch("http://localhost:4000/getRandomWord")
      .then(response => response.json())    
      .then(response => this.setState({ word: response.word }));
  }

  onLetterClick = (letter) =>
  {
    this.hiddenWord.current.updateWord(letter);
  }

  render()
  {
    return (
      <div className="game">
        <div className="first-part">
          <img className="img" alt={"hangman"} src={window.location.origin + this.imageFolder + this.imageName + this.imageExt} />
          <AlphabetTable className="alphabet-table" onLetterClick={this.onLetterClick} />
        </div>
        <div className="second-part">
          <HiddenWord ref={this.hiddenWord} word={this.state.word}/>             
        </div>
      </div>
    );
  }
}

class App extends React.Component
{
  constructor()
  {
    super();

    this.onNewGameClick = this.onNewGameClick.bind(this);

    this.state = { 
                    activeComponent: "menu",
                    components: { menu: <MenuList onNewGameClick={this.onNewGameClick}/>, game: <Game /> }
                 };
  }

  onNewGameClick()
  {
    this.setState({ activeComponent: "game" });
  }

  render()
  {
    return (
      <div className="App">
        { this.state.components[this.state.activeComponent] }
      </div>
    );
  }
}

export default App;
